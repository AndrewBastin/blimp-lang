package blimp.syntax

import blimp.lex.Token
import blimp.lex.tokens.singlechar.NewLineToken
import blimp.lex.tokens.singlechar.SemicolonToken
import blimp.util.toStack

internal sealed class Query(val tag: String?)
private class MatcherQuery(tag: String?, val required: Boolean, val predicate: (token: Token) -> Boolean): Query(tag)
private class ConsiderTakeAllQuery(tag: String?, val consider: (token: Token) -> Boolean, val predicate: (tokens: List<Token>) -> Boolean): Query(tag)

class ConsiderScope(private val tag: String?, private val consider: (token: Token) -> Boolean, private val matcher: NodeMatcher) {

    infix fun takeAll(predicate: (tokens: List<Token>) -> Boolean) {
        matcher.queries.add(ConsiderTakeAllQuery(tag, consider, predicate))
    }

}

class NodeMatcher {

    // Check whether the matcher ends the pattern with a semicolon or a new line token
    var checkTermination: Boolean = false

    // Whether not to ignore newline tokens encountered
    var dontEatNewLines: Boolean = false

    companion object {

        fun create(predicate: NodeMatcher.() -> Unit): NodeMatcher {
            return NodeMatcher().apply(predicate)
        }

    }

    internal val queries = mutableListOf<Query>()

    fun requiredToken(tag: String? = null, predicate: (token: Token) -> Boolean) {
        queries.add(MatcherQuery(tag, true, predicate))
    }

    fun optionalToken(tag: String? = null, predicate: (token: Token) -> Boolean) {
        queries.add(MatcherQuery(tag, false, predicate))
    }

    fun considerTokens(tag: String? = null, predicate: (token: Token) -> Boolean): ConsiderScope {
        return ConsiderScope(tag, predicate, this)
    }


    fun match(tokens: List<Token>): Boolean {
        return try {
            process(tokens)

            true
        } catch (e: Exception) {
            false
        }
    }

    fun getSingleTokenTags(tokens: List<Token>): Map<String, Token> {
        return process(tokens).second
    }

    fun getTokenCollectionTags(tokens: List<Token>): Map<String, List<Token>> {
        return process(tokens).third
    }

    fun getRemainder(tokens: List<Token>): List<Token> {
        return process(tokens).first
    }

    // To commonize getRemainder, getSingleTokenTags, getTokenCollectionTags
    // The triple returns the values for the functions as in the above order
    fun process(tokens: List<Token>) : Triple<List<Token>, Map<String, Token>, Map<String, List<Token>>> {

        val singleTokenTags = mutableMapOf<String, Token>()
        val tokenCollectionTags = mutableMapOf<String, List<Token>>()

        val tokensToValidate = tokens.toStack()

        for (query in queries) {

            if (query is MatcherQuery) {

                var token = tokensToValidate.pop()

                while (token is NewLineToken) token = tokensToValidate.pop()
                if (token is SemicolonToken) throw Exception("Semicolon terminated the matcher")

                if (!query.predicate(token)) {

                    // Required Query failed
                    if (query.required) throw Exception("Required query failed")
                    else tokensToValidate.push(token)

                } else {
                    val tag = query.tag
                    if (tag != null) {
                        singleTokenTags[tag] = token
                    }
                }

            } else if (query is ConsiderTakeAllQuery) {

                val tokenList = mutableListOf<Token>()


                fun clearNewlines() {
                    if (!dontEatNewLines) {
                        while (tokensToValidate.isNotEmpty() && tokensToValidate.peek() is NewLineToken) {
                            tokensToValidate.pop()
                            if (tokensToValidate.isNotEmpty() && tokensToValidate.peek() !is NewLineToken) {
                                val token = tokensToValidate.peek()

                                // Syntatically meaningful new line (next token maybe part of new statement, leave it to termination checks)
                                if (!query.consider(token)) {
                                    tokensToValidate.push(NewLineToken())
                                    return
                                }
                            }
                        }
                    }
                }

                while (tokensToValidate.isNotEmpty() && tokensToValidate.peek() is NewLineToken) tokensToValidate.pop()
                while (tokensToValidate.isNotEmpty() && tokensToValidate.peek() !is SemicolonToken && query.consider(tokensToValidate.peek())) {
                    tokenList.add(tokensToValidate.pop())

                    clearNewlines()
                }

                if (!query.predicate(tokenList)) throw Exception("Consider query failed")
                else {
                    val tag = query.tag

                    if (tag != null) {
                        tokenCollectionTags[tag] = tokenList
                    }
                }

            }

        }

        if (checkTermination && tokensToValidate.isNotEmpty()) {
            // The match should terminate the node emission with a semicolon or a new line
            if (tokensToValidate.peek() is NewLineToken || tokensToValidate.peek() is SemicolonToken) {
                tokensToValidate.pop()
            } else throw Exception("Failed termination check")
        }

        return Triple(tokensToValidate.reversed().toList(), singleTokenTags, tokenCollectionTags)

    }

}