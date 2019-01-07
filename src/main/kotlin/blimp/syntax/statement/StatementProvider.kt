package blimp.syntax.statement

import blimp.lex.Token

abstract class StatementProvider<T: Statement> {

    // Checks whether the Token chain matches any given grammar
    abstract fun matchTokenChain(tokens: List<Token>): Boolean
    abstract fun create(tokens: List<Token>): T

}