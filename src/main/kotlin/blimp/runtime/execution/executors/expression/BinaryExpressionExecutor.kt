package blimp.runtime.execution.executors.expression

import blimp.runtime.BlimpObject
import blimp.runtime.Environment
import blimp.syntax.expression.BinaryExpression

object BinaryExpressionExecutor: ExpressionExecutor<BinaryExpression>() {
    override fun evaluate(n: BinaryExpression, env: Environment): BlimpObject {
        return n.operator.evaluate(n.left, n.right, env)
    }
}