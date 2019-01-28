package blimp.syntax.statement.statements.assigns

import blimp.lex.Token
import blimp.lex.tokens.multichar.*
import blimp.lex.tokens.singlechar.AssignToken
import blimp.runtime.Environment
import blimp.syntax.Node
import blimp.syntax.NodeEmitter
import blimp.syntax.NodeMatcher
import blimp.syntax.expression.Expression
import blimp.syntax.statement.Statement

class CreateAssignStatement(val identifier: String, val expression: Expression, val mutable: Boolean = false): Statement() {

    companion object Emitter: NodeEmitter() {

        override val matcher = NodeMatcher.create {

            checkTermination = true

            requiredToken {
                it is KeywordToken && it.keyword == Keyword.Let
            }

            optionalToken("isVar") {
                it is KeywordToken && it.keyword == Keyword.Var
            }

            requiredToken("identifier") {
                it is IdentifierToken
            }

            requiredToken {
                it is AssignToken
            }

            considerTokens("expr") {
                Expression.isExpressionToken(it)
            } takeAll {
                Expression.matchTokenChain(it)
            }

        }

        override fun getNode(tokens: List<Token>): Node {

            matcher.getSingleTokenTags(tokens).apply {

                val isVariable = containsKey("isVar")
                val identifier = (this["identifier"] as? IdentifierToken) ?: throw Exception("Improper Identifier")
                val exprTokens = matcher.getTokenCollectionTags(tokens)["expr"] ?: throw Exception("Improper Expression")

                return CreateAssignStatement(identifier.identifier, Expression.create(exprTokens), isVariable)

            }
        }

    }

    override fun execute(env: Environment) {
        if (env.objects.containsKey(identifier)) throw Exception("Variable '$identifier' already declared")
        env.objects[identifier] = expression.evaluate(env).copy(mutable = this.mutable)
    }

}