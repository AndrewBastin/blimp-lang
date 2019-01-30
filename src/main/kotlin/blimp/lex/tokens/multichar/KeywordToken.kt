package blimp.lex.tokens.multichar

import blimp.lex.Token
import blimp.lex.TokenEmitter

enum class Keyword {
    Let, Var,

    // Ops
    Put, Get, Exec,

    If
}

// Identifier Tokens are converted to Keyword Tokens by KeywordReducer

data class KeywordToken(val keyword: Keyword): Token() {

    companion object Emitter : TokenEmitter() {
        override val REGEX: Regex = "((let)|(var)|(PUT)|(GET)|(EXEC)|(if))\\b".toRegex()

        override fun getToken(match: String) = KeywordToken(
            when (match) {

                "let"   -> Keyword.Let
                "var"   -> Keyword.Var
                "PUT"   -> Keyword.Put
                "GET"   -> Keyword.Get
                "EXEC"  -> Keyword.Exec
                "if"    -> Keyword.If

                else -> throw Exception("[KeywordToken] Failed match $match")

            }
        )

    }

}