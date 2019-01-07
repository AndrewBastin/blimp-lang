package blimp.syntax.statement.validation

import blimp.lex.Token
import blimp.util.toStack

private sealed class Query
private class RemainingValidatorQuery(val predicate: (remaining: List<Token>) -> Boolean): Query()
private class ValidatorQuery(val required: Boolean, val predicate: (token: Token) -> Boolean): Query()


class ValidatorScope(val tokens: List<Token>) {

    private val queries = mutableListOf<Query>()


    fun requiredToken(predicate: (token: Token) -> Boolean) {
        queries.add(ValidatorQuery(true, predicate))
    }

    fun optionalToken(predicate: (token: Token) -> Boolean) {
        queries.add(ValidatorQuery(false, predicate))
    }

    fun takeRemaining(predicate: (remaining: List<Token>) -> Boolean) {
        queries.add(RemainingValidatorQuery(predicate))
    }

    internal fun validate(): Boolean {

        val tokensToValidate = tokens.toStack()

        for (query in queries) {

            if (query is ValidatorQuery) {

                val token = tokensToValidate.pop()

                if (!query.predicate(token)) {
                    if (query.required) return false
                    else tokensToValidate.push(token)
                }

            } else if (query is RemainingValidatorQuery) {

                val remaining = tokensToValidate.reversed().toList()
                tokensToValidate.clear()

                if (!query.predicate(remaining)) return false
            }

        }


        return tokensToValidate.isEmpty()
    }

}

object StatementValidator {

    fun validate(tokens: List<Token>, validator: ValidatorScope.() -> Unit): Boolean {
        return ValidatorScope(tokens).apply {
            validator()
        }.validate()
    }

}