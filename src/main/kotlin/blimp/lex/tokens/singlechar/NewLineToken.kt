package blimp.lex.tokens.singlechar

import blimp.lex.Token
import blimp.lex.TokenEmitter

class NewLineToken: Token() {

    companion object Emitter: TokenEmitter() {

        override val REGEX = "\\n".toRegex()

        override fun getToken(match: String) = NewLineToken()


    }

}