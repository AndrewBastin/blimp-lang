package blimp.runtime.execution.executors.expression

import blimp.runtime.BlimpObject
import blimp.runtime.Environment
import blimp.syntax.expression.IdentifierExpression

object IdentifierExpressionExecutor: ExpressionExecutor<IdentifierExpression>() {
    override fun evaluate(n: IdentifierExpression, env: Environment): BlimpObject {
        return env.objects[n.identifier] ?: throw Exception("Variable '${n.identifier}' not defined")
    }

}