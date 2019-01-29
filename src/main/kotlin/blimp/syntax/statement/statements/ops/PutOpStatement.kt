package blimp.syntax.statement.statements.ops

import blimp.lex.Token
import blimp.lex.tokens.multichar.*
import blimp.runtime.Environment
import blimp.syntax.Node
import blimp.syntax.NodeEmitter
import blimp.syntax.NodeMatcher
import blimp.syntax.expression.Expression
import blimp.syntax.statement.Statement

class PutOpStatement(val expression: Expression): Statement() {

    companion object Emitter: NodeEmitter() {
        override val matcher = NodeMatcher.create {

            checkTermination = true

            requiredToken {
                it is KeywordToken && it.keyword == Keyword.Put
            }

            considerTokens("expr") {
                Expression.isExpressionToken(it)
            } takeAll {
                Expression.matchTokenChain(it)
            }

        }
        override fun getNode(tokens: List<Token>): Node {
            val expr = matcher.getTokenCollectionTags(tokens)["expr"]

            if (expr != null) {
                return PutOpStatement(Expression.create(expr))
            } else throw Exception("Improper expression")
        }

    }
}