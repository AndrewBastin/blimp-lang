package blimp.runtime.execution.executors.statement.assigns.operators

import blimp.runtime.Environment
import blimp.runtime.Executor
import blimp.runtime.execution.executors.statement.StatementExecutor
import blimp.syntax.statement.statements.assigns.operators.MultiplyAssignStatement

object MultiplyAssignStatementExecutor: StatementExecutor<MultiplyAssignStatement>() {
    override fun execute(n: MultiplyAssignStatement, env: Environment) {
        if (env.objects.contains(n.identifier)) {

            if (env.objects[n.identifier]!!.mutable) {

                val obj = Executor.evaluate(n.expr, env)

                if (obj.type == env.objects[n.identifier]!!.type) {

                    env.objects[n.identifier] = env.objects[n.identifier]!!.copy(
                        value = env.objects[n.identifier]!!.type.typeOperator.mul(env.objects[n.identifier]!!, obj).value
                    )

                } else throw Exception("Cannot assign a ${obj.type.typeName} to a ${env.objects[n.identifier]!!.type.typeName}")

            } else throw Exception("Variable '${n.identifier}' cannot be changed.")

        } else throw Exception("Variable '${n.identifier}' is not defined")
    }

}