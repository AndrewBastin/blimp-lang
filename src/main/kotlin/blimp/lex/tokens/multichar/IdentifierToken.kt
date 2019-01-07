package blimp.lex.tokens.multichar

import blimp.lex.Token
import blimp.lex.TokenEmitter

data class IdentifierToken(val identifier: String): Token() {

    companion object Emitter : TokenEmitter() {

        override val REGEX = "([a-zA-Z_][a-zA-Z0-9_]{0,31})\\b".toRegex()

        override fun getToken(match: String) = IdentifierToken(match)

    }

}