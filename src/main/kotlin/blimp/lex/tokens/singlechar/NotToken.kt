package blimp.lex.tokens.singlechar

import blimp.lex.Token
import blimp.lex.TokenEmitter

class NotToken: Token() {

    companion object Emitter : TokenEmitter() {

        override val REGEX = "!".toRegex()

        override fun getToken(match: String) = NotToken()

    }

}