package blimp.runtime.execution.executors.statement.assigns

import blimp.runtime.Environment
import blimp.runtime.Executor
import blimp.runtime.execution.executors.statement.StatementExecutor
import blimp.syntax.statement.statements.assigns.AssignStatement

object AssignStatementExecutor: StatementExecutor<AssignStatement>() {

    override fun execute(n: AssignStatement, env: Environment) {
        if (env.contains(n.identifier)) {

            if (env[n.identifier]!!.mutable) {

                val obj = Executor.evaluate(n.expression, env)

                if (obj.type == env[n.identifier]!!.type) {

                    env[n.identifier] = env[n.identifier]!!.copy(value = obj.value)

                } else throw Exception("Cannot assign a ${obj.type.typeName} to a ${env[n.identifier]!!.type.typeName}")

            } else throw Exception("Variable '${n.identifier}' cannot be changed.")

        } else throw Exception("Variable '${n.identifier}' is not defined")
    }

}