package blimp.runtime.execution.executors.expression.operators

import blimp.runtime.BlimpObject
import blimp.runtime.Environment
import blimp.runtime.Executor
import blimp.syntax.expression.Expression

object AddOperator: BinaryOperator() {
    override fun evaluate(left: Expression, right: Expression, env: Environment): BlimpObject {
        val a = Executor.evaluate(left, env)
        val b = Executor.evaluate(right, env)
        return a.type.typeOperator.add(a, b)
    }
}

object SubtractOperator: BinaryOperator() {
    override fun evaluate(left: Expression, right: Expression, env: Environment): BlimpObject {
        val a = Executor.evaluate(left, env)
        val b = Executor.evaluate(right, env)
        return a.type.typeOperator.sub(a, b)
    }
}

object MultiplicationOperator: BinaryOperator() {
    override fun evaluate(left: Expression, right: Expression, env: Environment): BlimpObject {
        val a = Executor.evaluate(left, env)
        val b = Executor.evaluate(right, env)
        return a.type.typeOperator.mul(a, b)
    }
}

object DivisionOperator: BinaryOperator() {
    override fun evaluate(left: Expression, right: Expression, env: Environment): BlimpObject {
        val a = Executor.evaluate(left, env)
        val b = Executor.evaluate(right, env)
        return a.type.typeOperator.div(a, b)
    }
}