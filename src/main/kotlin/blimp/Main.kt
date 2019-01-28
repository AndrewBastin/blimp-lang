package blimp

import blimp.lex.Lexer
import blimp.runtime.Environment
import blimp.syntax.Parser
import blimp.syntax.closures.REPLBlock

fun main() {

    println("Blimp Interactive Console")
    println()

    val env = Environment()

    while (true) {

        print(">>> ")
        val command = readLine()!!

        try {
//            val tokens = Lexer.lex(command)
//
//            //println(tokens)
//
//            val statement = TokenProcessor.getStatementFromTokenChain(tokens)
//
//            if (statement is Expression) {
//                val eval = statement.evaluate(env)
//                println(" -> $eval \t\t [${eval.type.typeName}]")
//            } else statement.execute(env)

            val tokens = Lexer.lex(command)
            val nodes = Parser.getNodes(tokens)
            val block = REPLBlock(nodes)

            if (block.canEvaluate) {
                val eval = block.evaluate(env)
                println(" -> $eval \t\t [${eval.type.typeName}]")
            } else block.execute(env)

        } catch (e: Exception) {
            println(e.message)
        }

    }

}