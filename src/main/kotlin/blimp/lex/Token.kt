package blimp.lex

import java.util.regex.Pattern

abstract class TokenEmitter {

    abstract val REGEX: Regex

    private val pattern by lazy { Pattern.compile(REGEX.toString()) }

    fun canEmit(input: String): Boolean {
        val match = REGEX.find(input)

        return match != null && match.range.start == 0
    }

    // Returns the remaining lexer string and the emitted token
    fun emitToken(input: String): Pair<String, Token> {
        val match = REGEX.find(input)

        if (match != null) {

            if (match.range.endInclusive == input.length - 1) return Pair("", getToken(match.value))
            return Pair(input.substring(match.range.endInclusive + 1), getToken(match.value))

        } else throw Exception("Token Emission with a null match $input")

    }

    protected abstract fun getToken(match: String): Token
}

abstract class Token
