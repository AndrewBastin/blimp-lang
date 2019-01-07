package blimp.syntax.statement.statements.assigns

import blimp.lex.Token
import blimp.lex.tokens.multichar.IdentifierToken
import blimp.lex.tokens.singlechar.AssignToken
import blimp.runtime.Environment
import blimp.syntax.statement.Statement
import blimp.syntax.statement.StatementProvider
import blimp.syntax.statement.statements.ExpressionStatement
import blimp.syntax.statement.validation.StatementValidator

class AssignStatement(val identifier: String, val expression: ExpressionStatement): Statement() {

    companion object: StatementProvider<AssignStatement>() {

        override fun matchTokenChain(tokens: List<Token>): Boolean {

            return StatementValidator.validate(tokens) {

                requiredToken {
                    it is IdentifierToken
                }

                requiredToken {
                    it is AssignToken
                }

                takeRemaining {
                    ExpressionStatement.matchTokenChain(it)
                }

            }

        }

        override fun create(tokens: List<Token>): AssignStatement {

            lateinit var identifier: String
            lateinit var expression: ExpressionStatement

            StatementValidator.validate(tokens) {

                requiredToken {
                    if (it is IdentifierToken) {
                        identifier = it.identifier

                        true
                    } else false
                }

                requiredToken {
                    it is AssignToken
                }

                takeRemaining {
                    if (ExpressionStatement.matchTokenChain(it)) {
                        expression = ExpressionStatement.create(it)

                        true
                    } else false
                }

            }

            return AssignStatement(identifier, expression)
        }

    }


    override fun execute(env: Environment) {
        if (env.objects.contains(identifier)) {

            if (env.objects[identifier]!!.mutable) {

                val obj = expression.evaluate(env)

                if (obj.type == env.objects[identifier]!!.type) {

                    env.objects[identifier] = env.objects[identifier]!!.copy(value = obj.value)

                } else throw Exception("Cannot assign a ${obj.type.typeName} to a ${env.objects[identifier]!!.type.typeName}")

            } else throw Exception("Variable '$identifier' cannot be changed.")

        } else throw Exception("Variable '$identifier' is not defined")
    }

}