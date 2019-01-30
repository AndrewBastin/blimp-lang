package blimp.syntax.closures

import blimp.lex.Token
import blimp.lex.tokens.singlechar.CurlyBraceToken
import blimp.lex.tokens.singlechar.CurlyBraceType
import blimp.lex.tokens.singlechar.SemicolonToken
import blimp.syntax.Node
import blimp.syntax.NodeEmitter
import blimp.syntax.NodeMatcher
import blimp.syntax.Parser

class ClosureBlock(children: List<Node>): Block(children) {

    override val canEvaluate = false

    companion object {

        fun applyClosureMatching(tag: String, matcher: NodeMatcher) {

            matcher.apply {
                dontEatNewLines = true

                var blockIndex = 1

                requiredToken {
                    it is CurlyBraceToken && it.type == CurlyBraceType.Opening
                }

                considerTokens(tag) {
                    if (it is CurlyBraceToken) {
                        if (it.type == CurlyBraceType.Opening) blockIndex++
                        else blockIndex--

                        if (blockIndex <= 0) {
                            blockIndex = 1 // Resetting for next execution of the function
                            false
                        } else true
                    } else true
                } takeAll {
                    true
                }

                requiredToken {
                    it is CurlyBraceToken && it.type == CurlyBraceType.Closing
                }
            }


        }

        val Emitter = object:NodeEmitter() {
            override val matcher = NodeMatcher.create {
                applyClosureMatching("contents", this)
            }

            override fun getNode(tokens: List<Token>): Node {
                val closureTokens = matcher.getTokenCollectionTags(tokens)["contents"]?.toMutableList() ?: throw Exception("Improper contents")
                val nodes = Parser.getNodes(closureTokens)
                return ClosureBlock(nodes)
            }

        }
    }

}