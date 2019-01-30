package blimp.lex.tokens.singlechar

import blimp.lex.Token
import blimp.lex.TokenEmitter

enum class CurlyBraceType {
    Opening,
    Closing
}

data class CurlyBraceToken(val type: CurlyBraceType): Token() {

    companion object Emitter: TokenEmitter() {

        override val REGEX = "[{|}]".toRegex()

        override fun getToken(match: String): Token {
            return CurlyBraceToken(
                if (match == "{") CurlyBraceType.Opening
                else CurlyBraceType.Closing
            )
        }

    }

}