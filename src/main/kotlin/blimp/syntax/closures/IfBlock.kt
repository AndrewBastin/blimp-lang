package blimp.syntax.closures

import blimp.lex.Token
import blimp.lex.tokens.multichar.Keyword
import blimp.lex.tokens.multichar.KeywordToken
import blimp.syntax.Node
import blimp.syntax.NodeEmitter
import blimp.syntax.NodeMatcher
import blimp.syntax.Parser
import blimp.syntax.expression.Expression

class IfBlock(val condition: Expression, children: List<Node>): Block(children) {
    override val canEvaluate = false

    companion object Emitter: NodeEmitter() {

        override val matcher = NodeMatcher.create {

            requiredToken {
                it is KeywordToken && it.keyword == Keyword.If
            }

            considerTokens("condition") {
                Expression.isExpressionToken(it)
            } takeAll {
                Expression.matchTokenChain(it)
            }

            ClosureBlock.applyClosureMatching("closure", this)

        }


        override fun getNode(tokens: List<Token>): Node {

            val tags = matcher.getTokenCollectionTags(tokens)
            val condition = tags["condition"] ?: throw Exception("Improper condition")
            val closure = tags["closure"] ?: throw Exception("Improper Closure")

            return IfBlock(Expression.create(condition), Parser.getNodes(closure))

        }

    }
}