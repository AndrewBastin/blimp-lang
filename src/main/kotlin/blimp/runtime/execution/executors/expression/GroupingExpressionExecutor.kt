package blimp.runtime.execution.executors.expression

import blimp.runtime.BlimpObject
import blimp.runtime.Environment
import blimp.runtime.Executor
import blimp.syntax.expression.GroupingExpression

object GroupingExpressionExecutor: ExpressionExecutor<GroupingExpression>() {
    override fun evaluate(n: GroupingExpression, env: Environment): BlimpObject {
        return Executor.evaluate(n.expression, env)
    }
}