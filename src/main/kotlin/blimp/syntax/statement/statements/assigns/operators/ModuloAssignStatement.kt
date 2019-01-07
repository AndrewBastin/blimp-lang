package blimp.syntax.statement.statements.assigns.operators

import blimp.lex.Token
import blimp.lex.tokens.doublechar.ModuloAssignToken
import blimp.lex.tokens.multichar.IdentifierToken
import blimp.runtime.Environment
import blimp.syntax.statement.Statement
import blimp.syntax.statement.StatementProvider
import blimp.syntax.statement.statements.ExpressionStatement
import blimp.syntax.statement.validation.StatementValidator

class ModuloAssignStatement(val identifier: String, val expr: ExpressionStatement): Statement() {

    companion object : StatementProvider<ModuloAssignStatement>() {
        override fun matchTokenChain(tokens: List<Token>) = StatementValidator.validate(tokens) {

            requiredToken {
                it is IdentifierToken
            }

            requiredToken {
                it is ModuloAssignToken
            }

            takeRemaining {
                ExpressionStatement.matchTokenChain(it)
            }

        }

        override fun create(tokens: List<Token>): ModuloAssignStatement {

            var identifier: String? = null
            var expr: ExpressionStatement? = null

            StatementValidator.validate(tokens) {

                requiredToken {
                    if (it is IdentifierToken) {
                        identifier = it.identifier

                        true
                    } else false
                }

                requiredToken {
                    it is ModuloAssignToken
                }

                takeRemaining {
                    expr = ExpressionStatement.create(it)

                    true
                }

            }


            return ModuloAssignStatement(
                identifier = identifier ?: throw Exception("Add Assign Statement parse failed, identifier null"),
                expr = expr ?: throw Exception("Add Assign Statement parse failed, expression null")
            )
        }

    }

    override fun execute(env: Environment) {
        if (env.objects.contains(identifier)) {

            if (env.objects[identifier]!!.mutable) {

                val obj = expr.evaluate(env)

                if (obj.type == env.objects[identifier]!!.type) {

                    env.objects[identifier] = env.objects[identifier]!!.copy(
                        value = env.objects[identifier]!!.type.typeOperator.mod(env.objects[identifier]!!, obj).value
                    )

                } else throw Exception("Cannot assign a ${obj.type.typeName} to a ${env.objects[identifier]!!.type.typeName}")

            } else throw Exception("Variable '$identifier' cannot be changed.")

        } else throw Exception("Variable '$identifier' is not defined")
    }

}