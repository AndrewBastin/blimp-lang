package blimp.syntax.statement.statements.ops

import blimp.lex.Token
import blimp.lex.tokens.multichar.IdentifierToken
import blimp.lex.tokens.multichar.Keyword
import blimp.lex.tokens.multichar.KeywordToken
import blimp.runtime.BlimpObject
import blimp.runtime.Environment
import blimp.runtime.typing.Types
import blimp.runtime.typing.types.primitive.*
import blimp.syntax.Node
import blimp.syntax.NodeEmitter
import blimp.syntax.NodeMatcher
import blimp.syntax.statement.Statement

class GetOpStatement(val identifier: String): Statement() {

    companion object Emitter: NodeEmitter() {

        override val matcher = NodeMatcher.create {

            checkTermination = true

            requiredToken {
                it is KeywordToken && it.keyword == Keyword.Get
            }

            requiredToken("identifier") {
                it is IdentifierToken
            }

        }

        override fun getNode(tokens: List<Token>): Node {
            val identifier = matcher.getSingleTokenTags(tokens)["identifier"]

            if (identifier != null && identifier is IdentifierToken) {
                return GetOpStatement(identifier.identifier)
            } else throw Exception("Improper Identifier")
        }

    }

}