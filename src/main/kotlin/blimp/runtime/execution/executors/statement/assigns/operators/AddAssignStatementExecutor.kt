package blimp.runtime.execution.executors.statement.assigns.operators

import blimp.runtime.Environment
import blimp.runtime.Executor
import blimp.runtime.execution.executors.statement.StatementExecutor
import blimp.syntax.statement.statements.assigns.operators.AddAssignStatement

object AddAssignStatementExecutor: StatementExecutor<AddAssignStatement>() {
    override fun execute(n: AddAssignStatement, env: Environment) {
        if (env.contains(n.identifier)) {

            if (env[n.identifier]!!.mutable) {

                val obj = Executor.evaluate(n.expr, env)

                if (obj.type == env[n.identifier]!!.type) {

                    env[n.identifier] = env[n.identifier]!!.copy(
                        value = env[n.identifier]!!.type.typeOperator.add(env[n.identifier]!!, obj).value
                    )

                } else throw Exception("Cannot assign a ${obj.type.typeName} to a ${env[n.identifier]!!.type.typeName}")

            } else throw Exception("Variable '${n.identifier}' cannot be changed.")

        } else throw Exception("Variable '${n.identifier}' is not defined")
    }
}