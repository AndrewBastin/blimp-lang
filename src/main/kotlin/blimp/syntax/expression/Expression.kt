package blimp.syntax.expression

import blimp.lex.Token
import blimp.lex.tokens.multichar.*
import blimp.lex.tokens.singlechar.BracketToken
import blimp.lex.tokens.singlechar.BracketType
import blimp.lex.tokens.singlechar.NotToken
import blimp.runtime.BlimpObject
import blimp.runtime.Environment
import blimp.runtime.typing.Type
import blimp.runtime.typing.Types
import blimp.syntax.Node
import blimp.syntax.NodeEmitter
import blimp.syntax.NodeMatcher
import blimp.runtime.execution.executors.expression.operators.BinaryOperator
import blimp.runtime.execution.executors.expression.operators.NotOperator
import blimp.runtime.execution.executors.expression.operators.UnaryOperator
import java.util.*
import kotlin.math.roundToInt

private fun convertToPostfix(tokens: List<Token>): List<Token> {
    val result = mutableListOf<Token>()

    val tokenStack = Stack<Token>()
    var bracketIndex = 0 // Should be 0 if the brackets are maintained

    for (token in tokens) {
        when (token) {
            is CharToken, is StringToken, is NumberToken, is IdentifierToken, is BooleanToken -> result.add(token)

            is NotToken -> {
                tokenStack.push(token)
            }

            is BinaryOpToken -> {
                // Operator precedence
                if (tokenStack.isNotEmpty()) {
                    val preToken = tokenStack.peek()

                    // Unary First
                    if (preToken is NotToken) {
                        result.add(tokenStack.pop())
                        tokenStack.add(token)
                    } else if (preToken is BinaryOpToken) {
                        if (token > preToken) {
                            result.add(tokenStack.pop())
                            tokenStack.add(token)
                        } else {
                            result.add(token)
                        }
                    } else if (preToken is BracketToken) {
                        tokenStack.push(token)
                    } else {
                        result.add(tokenStack.pop())
                        tokenStack.push(token)
                    }
                } else {
                    tokenStack.push(token)
                }
            }

            is BracketToken -> {

                // Bracket Check
                if (token.type == BracketType.Opening) bracketIndex++
                else bracketIndex--


                if (token.type == BracketType.Opening) tokenStack.push(token)
                else {
                    var opToken = tokenStack.pop()

                    do {
                        result.add(opToken)

                        opToken = tokenStack.pop()
                    } while (opToken !is BracketToken || opToken.type != BracketType.Opening)

                }
            }

        }
    }

    if (bracketIndex != 0) throw Exception("[expr] Improper brackets")

    // Pop all remaining tokens
    while (tokenStack.isNotEmpty()) result.add(tokenStack.pop())

    return result
}

abstract class Expression: Node() {

    override val canEvaluate = true
    override val isClosure = false

    companion object {

        // Expression as a line statement
        val Emitter = object: NodeEmitter() {

            override val matcher = NodeMatcher.create {
                checkTermination = true

                considerTokens("expr") {
                    isExpressionToken(it)
                } takeAll {
                    Expression.matchTokenChain(it)
                }
            }
            override fun getNode(tokens: List<Token>): Node {
                val exprTokens = matcher.getTokenCollectionTags(tokens)["expr"] ?: throw Exception("Improper Expression")

                return Expression.create(exprTokens)
            }

        }

        // Returns whether a given token can be a part of an expression
        fun isExpressionToken(token: Token): Boolean {
            return token is StringToken || token is NumberToken || token is BooleanToken
                    || token is BinaryOpToken || token is CharToken || token is NotToken || token is BracketToken || token is IdentifierToken
        }

        fun matchTokenChain(tokens: List<Token>): Boolean {
            try {

                // First we check if the token contains any statement-ey tokens
                // i.e any tokens which might result in a statement
                if (tokens.any {
                        !isExpressionToken(it)
                    }) return false

                // If we can formulate an expression, then we can form an expression token
                Expression.createExpression(tokens)
                return true

            } catch (e: Exception) {
                return false
            }
        }

        // Assumes it validates correctly
        fun create(tokens: List<Token>): Expression = createExpression(tokens)

        fun createExpression(tokens: List<Token>, stack: Stack<Expression> = Stack()): Expression {

            val orderedTokens = convertToPostfix(tokens)
            val expStack = stack

            val iterator = orderedTokens.iterator()

            while (iterator.hasNext()) {
                val token = iterator.next()

                when (token) {

                    is BooleanToken -> {
                        expStack.push(LiteralExpression(token.value, Types.bool))
                    }

                    is NumberToken -> {

                        if (token.floatingPoint) {
                            expStack.push(LiteralExpression(token.value, Types.float))
                        } else {
                            expStack.push(LiteralExpression(token.value.roundToInt(), Types.int))
                        }

                    }

                    is StringToken -> {

                        expStack.push(LiteralExpression(token.value, Types.string))

                    }

                    is CharToken -> {

                        expStack.push(LiteralExpression(token.value, Types.char))

                    }


                    is NotToken -> {
                        expStack.push(UnaryExpression(expStack.pop(), NotOperator))
                    }

                    is BinaryOpToken -> {
                        val right = expStack.pop()
                        val left = expStack.pop()
                        expStack.push(BinaryExpression(left, token.op.operator, right))
                    }

                    is IdentifierToken -> {
                        expStack.push(IdentifierExpression(token.identifier))
                    }

                }

            }

            if (expStack.empty()) throw Exception("[Expression/parser] Empty expression stack")
            if (expStack.size > 1) throw Exception("[Expression/parser] Incomplete expression")

            return expStack.pop()
        }

    }
}

class UnaryExpression(val expr: Expression, val operator: UnaryOperator): Expression()

class IdentifierExpression(val identifier: String): Expression()

class LiteralExpression(val value: Any, val type: Type): Expression()

class GroupingExpression(val expression: Expression): Expression()

class BinaryExpression(val left: Expression, val operator: BinaryOperator, val right: Expression): Expression()