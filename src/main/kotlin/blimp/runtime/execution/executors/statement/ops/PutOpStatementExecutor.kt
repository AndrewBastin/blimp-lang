package blimp.runtime.execution.executors.statement.ops

import blimp.runtime.Environment
import blimp.runtime.Executor
import blimp.runtime.execution.executors.statement.StatementExecutor
import blimp.syntax.statement.statements.ops.PutOpStatement

object PutOpStatementExecutor: StatementExecutor<PutOpStatement>() {
    override fun execute(n: PutOpStatement, env: Environment) {
        println(Executor.evaluate(n.expression, env).value)
    }

}