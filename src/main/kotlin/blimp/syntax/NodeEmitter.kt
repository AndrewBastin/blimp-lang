package blimp.syntax

import blimp.lex.Token

abstract class NodeEmitter {

    abstract val matcher: NodeMatcher

    fun canEmit(tokens: List<Token>): Boolean = matcher.match(tokens)

    // Returns the remaining unprocessed tokens and the emitted node
    fun emit(tokens: List<Token>): Pair<List<Token>, Node> {
        return matcher.getRemainder(tokens) to getNode(tokens)
    }

    // Emitter should implement how to extract the data
    abstract fun getNode(tokens: List<Token>): Node

}