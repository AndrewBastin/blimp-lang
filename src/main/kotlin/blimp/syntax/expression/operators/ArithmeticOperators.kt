package blimp.syntax.expression.operators

import blimp.runtime.BlimpObject
import blimp.runtime.Environment
import blimp.syntax.expression.Expression

object AddOperator: BinaryOperator() {
    override fun evaluate(left: Expression, right: Expression, env: Environment): BlimpObject {
        val a = left.evaluate(env)
        val b = right.evaluate(env)
        return a.type.typeOperator.add(a, b)
    }
}

object SubtractOperator: BinaryOperator() {
    override fun evaluate(left: Expression, right: Expression, env: Environment): BlimpObject {
        val a = left.evaluate(env)
        val b = right.evaluate(env)
        return a.type.typeOperator.sub(a, b)
    }
}

object MultiplicationOperator: BinaryOperator() {
    override fun evaluate(left: Expression, right: Expression, env: Environment): BlimpObject {
        val a = left.evaluate(env)
        val b = right.evaluate(env)
        return a.type.typeOperator.mul(a, b)
    }
}

object DivisionOperator: BinaryOperator() {
    override fun evaluate(left: Expression, right: Expression, env: Environment): BlimpObject {
        val a = left.evaluate(env)
        val b = right.evaluate(env)
        return a.type.typeOperator.div(a, b)
    }
}