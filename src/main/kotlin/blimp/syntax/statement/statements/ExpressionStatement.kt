package blimp.syntax.statement.statements

import blimp.lex.Token
import blimp.lex.tokens.singlechar.AssignToken
import blimp.runtime.BlimpObject
import blimp.runtime.Environment
import blimp.syntax.expression.Expression
import blimp.syntax.statement.Statement
import blimp.syntax.statement.StatementProvider

class ExpressionStatement(val expression: Expression): Statement() {

    companion object: StatementProvider<ExpressionStatement>() {

        override fun matchTokenChain(tokens: List<Token>): Boolean {
            try {

                // First we check if the token contains any statement-ey tokens
                // i.e any tokens which might result in a statement
                if (tokens.any {
                        it is Statement || it is AssignToken
                    }) return false

                // If we can formulate an expression, then we can form an expression token
                Expression.createExpression(tokens)
                return true

            } catch (e: Exception) {
                return false
            }
        }

        // Assumes it validates correctly
        override fun create(tokens: List<Token>): ExpressionStatement =
            ExpressionStatement(Expression.createExpression(tokens))

    }

    fun evaluate(env: Environment): BlimpObject = expression.evaluate(env)

    override fun execute(env: Environment) {
        expression.evaluate(env)
    }
}