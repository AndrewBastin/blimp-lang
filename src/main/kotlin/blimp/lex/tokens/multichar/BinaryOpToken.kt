package blimp.lex.tokens.multichar

import blimp.lex.Token
import blimp.lex.TokenEmitter
import blimp.runtime.execution.executors.expression.operators.*

enum class BinaryOp(val precedence: Int, val operator: BinaryOperator) {

    As(0, AsOperator),
    Is(0, IsOperator),

    Equal(0, EqualOperator),
    NotEqual(0, NotEqualOperator),

    Greater(1, GreaterOperator),
    Lesser(1, LesserOperator),
    GreaterEqual(1, GreaterEqualOperator),
    LesserEqual(1, LesserEqualOperator),

    Or(2, OrOperator),
    And(2, AndOperator),

    Addition(3, AddOperator),
    Subtraction(3, SubtractOperator),

    Multiplication(4, MultiplicationOperator),
    Division(4, DivisionOperator)
}

data class BinaryOpToken(val op: BinaryOp): Token(), Comparable<BinaryOpToken> {

    // Used for operator precedence (Check blimp.syntax.expression.Expression)
    override fun compareTo(other: BinaryOpToken): Int = this.op.precedence.compareTo(other.op.precedence)

    companion object Emitter : TokenEmitter() {

        override val REGEX: Regex = "([*|/|+|\\-|&|||>|<|]|(>=)|(<=)|(==)|(!=)|(as)|(is))".toRegex()

        override fun getToken(match: String) = BinaryOpToken(

            when (match) {

                "+" -> BinaryOp.Addition
                "-" -> BinaryOp.Subtraction
                "/" -> BinaryOp.Division
                "*" -> BinaryOp.Multiplication
                "&" -> BinaryOp.And
                "|" -> BinaryOp.Or
                ">" -> BinaryOp.Greater
                "<" -> BinaryOp.Lesser
                ">=" -> BinaryOp.GreaterEqual
                "<=" -> BinaryOp.LesserEqual
                "==" -> BinaryOp.Equal
                "!=" -> BinaryOp.NotEqual
                "as" -> BinaryOp.As
                "is" -> BinaryOp.Is

                else -> throw Exception("[token/BinaryOpToken] Invalid input($match), make sure input was validated")

            }
        )

    }

}