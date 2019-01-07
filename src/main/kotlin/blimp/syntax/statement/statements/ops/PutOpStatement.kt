package blimp.syntax.statement.statements.ops

import blimp.lex.Token
import blimp.lex.tokens.multichar.Keyword
import blimp.lex.tokens.multichar.KeywordToken
import blimp.runtime.Environment
import blimp.syntax.statement.Statement
import blimp.syntax.statement.StatementProvider
import blimp.syntax.statement.statements.ExpressionStatement
import blimp.syntax.statement.validation.StatementValidator

class PutOpStatement(val expression: ExpressionStatement): Statement() {

    override fun execute(env: Environment) {
        println(expression.evaluate(env).value)
    }

    companion object : StatementProvider<PutOpStatement>() {

        override fun matchTokenChain(tokens: List<Token>) = StatementValidator.validate(tokens) {

            requiredToken {
                it is KeywordToken && it.keyword == Keyword.Put
            }

            takeRemaining {
                ExpressionStatement.matchTokenChain(it)
            }

        }

        override fun create(tokens: List<Token>): PutOpStatement {

            var expr: ExpressionStatement? = null

            StatementValidator.validate(tokens) {

                requiredToken {
                    it is KeywordToken
                }

                takeRemaining {
                    expr = ExpressionStatement.create(it)

                    ExpressionStatement.matchTokenChain(it)
                }

            }

            return PutOpStatement(expr ?: throw Exception("Put Op failed, cannot parse expression"))

        }

    }

}