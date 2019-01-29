package blimp.lex.tokens.multichar

import blimp.lex.Token
import blimp.lex.TokenEmitter

class MultilineCommentToken(val comment: String): Token() {

    companion object Emitter: TokenEmitter() {
        override val REGEX = "\\/\\*(\\*(?!\\/)|[^*])*\\*\\/".toRegex()

        override fun getToken(match: String): Token {
            return MultilineCommentToken(match.substring(2, match.length - 2))
        }

    }

}