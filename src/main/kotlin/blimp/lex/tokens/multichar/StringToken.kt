package blimp.lex.tokens.multichar

import blimp.lex.Token
import blimp.lex.TokenEmitter

data class StringToken(val value: String): Token() {

    companion object Emitter : TokenEmitter() {

        override val REGEX: Regex = "\\\"(([^\\\"]|\\\\\\\")*[^\\\\])?\\\"".toRegex()
        override fun getToken(match: String): Token = StringToken(
            match.substring(1, match.length - 1)
        )

    }

}