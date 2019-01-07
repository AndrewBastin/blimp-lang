package blimp.syntax.statement.statements.assigns

import blimp.lex.Token
import blimp.lex.tokens.multichar.IdentifierToken
import blimp.lex.tokens.multichar.Keyword
import blimp.lex.tokens.multichar.KeywordToken
import blimp.lex.tokens.singlechar.AssignToken
import blimp.runtime.Environment
import blimp.syntax.statement.Statement
import blimp.syntax.statement.StatementProvider
import blimp.syntax.statement.statements.ExpressionStatement
import blimp.syntax.statement.validation.StatementValidator

class CreateAssignStatement(val identifier: String, val expression: ExpressionStatement, val mutable: Boolean = false): Statement() {

    companion object: StatementProvider<CreateAssignStatement>() {

        override fun matchTokenChain(tokens: List<Token>) = StatementValidator.validate(tokens) {

            requiredToken {
                it is KeywordToken && it.keyword == Keyword.Let
            }

            optionalToken {
                it is KeywordToken && it.keyword == Keyword.Var
            }

            requiredToken {
                it is IdentifierToken
            }

            requiredToken {
                it is AssignToken
            }

            takeRemaining {
                ExpressionStatement.matchTokenChain(it.reversed())
            }

        }

        // Assumed this is validated
        override fun create(tokens: List<Token>): CreateAssignStatement {

            lateinit var identifier: String
            lateinit var expression: ExpressionStatement

            var mutable = false

            StatementValidator.validate(tokens) {

                requiredToken {
                    it is KeywordToken && it.keyword == Keyword.Let
                }

                optionalToken {
                    if (it is KeywordToken && it.keyword == Keyword.Var) {
                        mutable = true

                        true
                    } else false
                }

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

            return CreateAssignStatement(identifier, expression, mutable)
        }

    }

    override fun execute(env: Environment) {
        if (env.objects.containsKey(identifier)) throw Exception("Variable '$identifier' already declared")
        env.objects[identifier] = expression.evaluate(env).copy(mutable = this.mutable)
    }

}