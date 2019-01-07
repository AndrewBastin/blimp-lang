package blimp.lex.tokens.multichar

import blimp.lex.Token
import blimp.lex.TokenEmitter

data class CharToken(val value: Char): Token() {

    companion object Emitter : TokenEmitter() {

        override val REGEX: Regex = "'(([^'\\\\\\n]|\\\\.))'".toRegex()
        override fun getToken(match: String): Token = CharToken(

            match[1]

        )

    }

}