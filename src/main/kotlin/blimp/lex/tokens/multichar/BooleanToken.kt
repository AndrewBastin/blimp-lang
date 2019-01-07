package blimp.lex.tokens.multichar

import blimp.lex.Token
import blimp.lex.TokenEmitter

data class BooleanToken(val value: Boolean): Token() {

    companion object Emitter : TokenEmitter() {

        override val REGEX: Regex = "(true)|(false)".toRegex()

        override fun getToken(match: String): Token {
            return BooleanToken(
                when (match) {
                    "true" -> true
                    "false" -> false

                    else -> throw Exception("[BooleanToken] Cannot getToken with match : $match")
                }
            )
        }

    }

}