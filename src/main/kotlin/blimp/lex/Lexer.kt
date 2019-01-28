package blimp.lex

import blimp.lex.tokens.doublechar.*
import blimp.lex.tokens.multichar.*
import blimp.lex.tokens.singlechar.*

object Lexer {

    private val emitters = arrayOf(

        // Order Matters!!!

        // Double Char
        AddAssignToken.Emitter,
        SubtractAssignToken.Emitter,
        MultiplyAssignToken.Emitter,
        DivideAssignToken.Emitter,
        ModuloAssignToken.Emitter,

        // Multi Char
        BinaryOpToken.Emitter,
        CharToken.Emitter,
        StringToken.Emitter,
        KeywordToken.Emitter,
        BooleanToken.Emitter,
        NumberToken.Emitter,
        IdentifierToken.Emitter,

        // Single Char
        AssignToken.Emitter,
        BracketToken.Emitter,
        DotToken.Emitter,
        NotToken.Emitter,
        WhitespaceToken.Emitter,
        SemicolonToken.Emitter,
        NewLineToken.Emitter

    )

    fun lex(input: String): List<Token> {

        var working = input
        val tokens = mutableListOf<Token>()

        while (working.isNotEmpty()) {
            val emitter = emitters.find { it.canEmit(working) }
            if (emitter != null) {
                val (newWorking, token) = emitter.emitToken(working)
                tokens.add(token)
                working = newWorking
            } else throw Exception("[lex] Failed lexing ($working)")
        }

        // Filter Whitespace Tokens
        tokens.removeAll { it is WhitespaceToken }

        return tokens
    }

}
