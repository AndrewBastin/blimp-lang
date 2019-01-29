package blimp.runtime.execution.executors.expression.operators

import blimp.runtime.BlimpObject
import blimp.runtime.Environment
import blimp.runtime.Executor
import blimp.syntax.expression.Expression

object AndOperator: BinaryOperator() {

    override fun evaluate(left: Expression, right: Expression, env: Environment): BlimpObject {

        val a = Executor.evaluate(left, env)
        val b = Executor.evaluate(right, env)
        return a.type.typeOperator.and(a, b)

    }

}

object OrOperator: BinaryOperator() {

    override fun evaluate(left: Expression, right: Expression, env: Environment): BlimpObject {
        val a = Executor.evaluate(left, env)
        val b = Executor.evaluate(right, env)
        return a.type.typeOperator.or(a, b)
    }

}

object NotOperator: UnaryOperator() {

    override fun evaluate(expr: Expression, env: Environment): BlimpObject {
        val obj = Executor.evaluate(expr, env)

        return obj.type.typeOperator.not(obj)
    }

}