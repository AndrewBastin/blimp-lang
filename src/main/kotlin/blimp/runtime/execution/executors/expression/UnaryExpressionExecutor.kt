package blimp.runtime.execution.executors.expression

import blimp.runtime.BlimpObject
import blimp.runtime.Environment
import blimp.syntax.expression.UnaryExpression

object UnaryExpressionExecutor: ExpressionExecutor<UnaryExpression>() {

    override fun evaluate(n: UnaryExpression, env: Environment): BlimpObject {
        return n.operator.evaluate(n.expr, env)
    }

}