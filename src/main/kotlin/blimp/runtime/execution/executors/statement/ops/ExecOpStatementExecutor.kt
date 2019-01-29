package blimp.runtime.execution.executors.statement.ops

import blimp.lex.Lexer
import blimp.runtime.Environment
import blimp.runtime.Executor
import blimp.runtime.execution.executors.statement.StatementExecutor
import blimp.syntax.Parser
import blimp.syntax.closures.FileBlock
import blimp.syntax.statement.statements.ops.ExecOpStatement
import java.io.File
import kotlin.system.measureTimeMillis

object ExecOpStatementExecutor: StatementExecutor<ExecOpStatement>() {
    override fun execute(n: ExecOpStatement, env: Environment) {
        val time = measureTimeMillis {
            Executor.execute(
                FileBlock(
                    Parser.getNodes(
                        Lexer.lex(
                            File(n.filePath).readText()
                        )
                    )
                )
                , env
            )
        }

        println("\nCompleted Execution in $time ms")
    }

}