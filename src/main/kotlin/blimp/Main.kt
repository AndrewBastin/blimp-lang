package blimp

import blimp.lex.Lexer
import blimp.runtime.Environment
import blimp.syntax.TokenProcessor
import blimp.syntax.statement.statements.ExpressionStatement

fun main() {

    println("Blimp Interactive Console")
    println()

    val env = Environment()

    while (true) {

        print(">>> ")
        val command = readLine()!!

        try {
            val tokens = Lexer.lex(command)

            //println(tokens)

            val statement = TokenProcessor.getStatementFromTokenChain(tokens)

            if (statement is ExpressionStatement) {
                val eval = statement.evaluate(env)
                println(" -> $eval \t\t [${eval.type.typeName}]")
            } else statement.execute(env)

        } catch (e: Exception) {
            println(e.message)
        }

    }

}