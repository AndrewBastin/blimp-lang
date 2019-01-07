package blimp.lex.tokens.singlechar

import blimp.lex.Token
import blimp.lex.TokenEmitter

enum class BracketType {

    Closing,
    Opening

}

data class BracketToken(val type: BracketType): Token() {

    companion object Emitter : TokenEmitter() {

        override val REGEX: Regex = "[(|)]".toRegex()

        override fun getToken(match: String) = BracketToken(
            when (match) {
                "(" -> BracketType.Opening
                ")" -> BracketType.Closing

                else -> throw Exception("[token/BracketToken] Failed match : $match")
            }
        )

    }

}