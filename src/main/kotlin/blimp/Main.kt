package blimp

import blimp.lex.Lexer
import blimp.runtime.Environment
import blimp.runtime.Executor
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
            val tokens = Lexer.lex(command)
            val nodes = Parser.getNodes(tokens)
            val block = REPLBlock(nodes)

            if (block.canEvaluate) {
                val eval = Executor.evaluate(block, env)
                println(" -> $eval \t\t [${eval.type.typeName}]")
            } else Executor.execute(block, env)

        } catch (e: Exception) {
            println(e.message)
        }

    }

}