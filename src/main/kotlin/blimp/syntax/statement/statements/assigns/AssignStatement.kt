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

class AssignStatement(val identifier: String, val expression: Expression): Statement() {

    companion object Emitter: NodeEmitter() {

        override val matcher = NodeMatcher.create {

            checkTermination = true

            requiredToken("ident") {
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

            val identifier = (matcher.getSingleTokenTags(tokens)["ident"] as? IdentifierToken) ?: throw Exception("Improper identifier")
            val exprTokens = matcher.getTokenCollectionTags(tokens)["expr"] ?: throw Exception("Improper expression")

            return AssignStatement(identifier.identifier, Expression.create(exprTokens))
        }

    }

}