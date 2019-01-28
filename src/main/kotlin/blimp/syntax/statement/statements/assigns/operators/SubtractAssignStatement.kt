package blimp.syntax.statement.statements.assigns.operators

import blimp.lex.Token
import blimp.lex.tokens.doublechar.SubtractAssignToken
import blimp.lex.tokens.multichar.*
import blimp.runtime.Environment
import blimp.syntax.Node
import blimp.syntax.NodeEmitter
import blimp.syntax.NodeMatcher
import blimp.syntax.expression.Expression
import blimp.syntax.statement.Statement

class SubtractAssignStatement(val identifier: String, val expr: Expression): Statement() {

    companion object Emitter: NodeEmitter() {

        override val matcher = NodeMatcher.create {

            checkTermination = true

            requiredToken("ident") {
                it is IdentifierToken
            }

            requiredToken {
                it is SubtractAssignToken
            }

            considerTokens("expr") {
                Expression.isExpressionToken(it)
            } takeAll {
                Expression.matchTokenChain(it)
            }

        }

        override fun getNode(tokens: List<Token>): Node {

            val identifier = (matcher.getSingleTokenTags(tokens)["ident"] as? IdentifierToken) ?: throw Exception("Improper identifier")
            val exprTokens = matcher.getTokenCollectionTags(tokens)["expr"] ?: throw Exception("Improper expression")

            return SubtractAssignStatement(identifier.identifier, Expression.create(exprTokens))

        }

    }

    override fun execute(env: Environment) {
        if (env.objects.contains(identifier)) {

            if (env.objects[identifier]!!.mutable) {

                val obj = expr.evaluate(env)

                if (obj.type == env.objects[identifier]!!.type) {

                    env.objects[identifier] = env.objects[identifier]!!.copy(
                        value = env.objects[identifier]!!.type.typeOperator.sub(env.objects[identifier]!!, obj).value
                    )

                } else throw Exception("Cannot assign a ${obj.type.typeName} to a ${env.objects[identifier]!!.type.typeName}")

            } else throw Exception("Variable '$identifier' cannot be changed.")

        } else throw Exception("Variable '$identifier' is not defined")
    }

}