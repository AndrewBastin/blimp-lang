package blimp.lex.tokens.multichar

import blimp.lex.Token
import blimp.lex.TokenEmitter

// Floating Point defines that the literal is a floating point value (ie, reduced by the NumberReducer to a float)
data class NumberToken(val value: Float, val floatingPoint: Boolean = false): Token() {

    companion object Emitter : TokenEmitter() {

        override val REGEX = "[-]?[0-9]*\\.?[0-9]+".toRegex()

        override fun getToken(match: String): NumberToken = NumberToken(match.toFloat(), match.contains("."))
    }

}