package blimp.syntax.expression.operators

import blimp.runtime.BlimpObject
import blimp.runtime.Environment
import blimp.syntax.expression.Expression

object AndOperator: BinaryOperator() {

    override fun evaluate(left: Expression, right: Expression, env: Environment): BlimpObject {

        val a = left.evaluate(env)
        val b = right.evaluate(env)
        return a.type.typeOperator.and(a, b)

    }

}

object OrOperator: BinaryOperator() {

    override fun evaluate(left: Expression, right: Expression, env: Environment): BlimpObject {
        val a = left.evaluate(env)
        val b = right.evaluate(env)
        return a.type.typeOperator.or(a, b)
    }

}

object NotOperator: UnaryOperator() {

    override fun evaluate(expr: Expression, env: Environment): BlimpObject {
        val obj = expr.evaluate(env)

        return obj.type.typeOperator.not(obj)
    }

}