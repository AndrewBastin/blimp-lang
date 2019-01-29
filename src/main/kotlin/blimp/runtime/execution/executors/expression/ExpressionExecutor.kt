package blimp.runtime.execution.executors.expression

import blimp.runtime.Environment
import blimp.runtime.execution.NodeExecutor
import blimp.syntax.expression.Expression

abstract class ExpressionExecutor<T: Expression>: NodeExecutor<T>() {

    override fun execute(n: T, env: Environment) {
        evaluate(n, env) // Just evaluate the code for execution
    }

}