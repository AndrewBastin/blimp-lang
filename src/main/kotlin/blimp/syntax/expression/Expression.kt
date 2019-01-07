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
import blimp.syntax.expression.operators.BinaryOperator
import blimp.syntax.expression.operators.NotOperator
import blimp.syntax.expression.operators.UnaryOperator
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

abstract class Expression {

    companion object {

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

    abstract fun evaluate(env: Environment): BlimpObject

}

class UnaryExpression(private val expr: Expression, private val operator: UnaryOperator): Expression() {

    override fun evaluate(env: Environment): BlimpObject = operator.evaluate(expr, env)
}

class IdentifierExpression(private val identifier: String): Expression() {

    override fun evaluate(env: Environment): BlimpObject = env.objects[identifier] ?: throw Exception("Variable '$identifier' not defined")

}

class LiteralExpression(private val value: Any, private val type: Type): Expression() {

    override fun evaluate(env: Environment): BlimpObject {
        return BlimpObject(type, value)
    }

}

class GroupingExpression(private val expression: Expression): Expression() {

    override fun evaluate(env: Environment): BlimpObject = expression.evaluate(env)

}

class BinaryExpression(private val left: Expression, private val operator: BinaryOperator, private val right: Expression): Expression() {

    override fun evaluate(env: Environment): BlimpObject = operator.evaluate(left, right, env)


}