package blimp.lex.tokens.multichar

import blimp.lex.Token
import blimp.lex.TokenEmitter

data class SingleLineCommentToken(val comment: String): Token() {

    companion object Emitter: TokenEmitter() {

        override val REGEX = "[/][/].*".toRegex()

        override fun getToken(match: String): Token {
            return SingleLineCommentToken(match.substring(2))
        }

    }

}