package blimp.runtime.execution.executors.expression

import blimp.runtime.BlimpObject
import blimp.runtime.Environment
import blimp.syntax.expression.LiteralExpression

object LiteralExpressionExecutor: ExpressionExecutor<LiteralExpression>() {
    override fun evaluate(n: LiteralExpression, env: Environment): BlimpObject {
        return BlimpObject(n.type, n.value)
    }
}