package blimp.lex.tokens.singlechar

import blimp.lex.Token
import blimp.lex.TokenEmitter

class AssignToken: Token() {

    companion object Emitter : TokenEmitter() {

        override val REGEX: Regex = "=".toRegex()

        override fun getToken(match: String): Token = AssignToken()

    }

}