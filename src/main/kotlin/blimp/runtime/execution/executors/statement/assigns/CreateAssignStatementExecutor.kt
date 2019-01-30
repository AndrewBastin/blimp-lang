package blimp.runtime.execution.executors.statement.assigns

import blimp.runtime.Environment
import blimp.runtime.Executor
import blimp.runtime.execution.executors.statement.StatementExecutor
import blimp.syntax.statement.statements.assigns.CreateAssignStatement

object CreateAssignStatementExecutor: StatementExecutor<CreateAssignStatement>() {

    override fun execute(n: CreateAssignStatement, env: Environment) {
        // Overriding checkParents to allow variable shadowing
        if (env.contains(n.identifier, false)) throw Exception("Variable '${n.identifier}' already declared")
        env.createObject(n.identifier, Executor.evaluate(n.expression, env).copy(mutable = n.mutable))
    }

}