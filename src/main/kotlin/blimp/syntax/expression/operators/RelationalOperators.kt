package blimp.syntax.expression.operators

import blimp.runtime.BlimpObject
import blimp.runtime.Environment
import blimp.syntax.expression.Expression

object EqualOperator: BinaryOperator() {

    override fun evaluate(left: Expression, right: Expression, env: Environment): BlimpObject {
        val a = left.evaluate(env)
        val b = right.evaluate(env)

        return a.type.typeOperator.equal(a, b)
    }

}

object NotEqualOperator: BinaryOperator() {

    override fun evaluate(left: Expression, right: Expression, env: Environment): BlimpObject {
        val a = left.evaluate(env)
        val b = right.evaluate(env)

        return a.type.typeOperator.notEqual(a, b)
    }

}

object GreaterOperator: BinaryOperator() {

    override fun evaluate(left: Expression, right: Expression, env: Environment): BlimpObject {

        val a = left.evaluate(env)
        val b = right.evaluate(env)

        return a.type.typeOperator.greater(a , b)

    }

}

object GreaterEqualOperator: BinaryOperator() {

    override fun evaluate(left: Expression, right: Expression, env: Environment): BlimpObject {

        val a = left.evaluate(env)
        val b = right.evaluate(env)

        return a.type.typeOperator.greaterEqual(a , b)


    }

}

object LesserOperator: BinaryOperator() {

    override fun evaluate(left: Expression, right: Expression, env: Environment): BlimpObject {

        val a = left.evaluate(env)
        val b = right.evaluate(env)

        return a.type.typeOperator.lesser(a , b)


    }

}

object LesserEqualOperator: BinaryOperator() {

    override fun evaluate(left: Expression, right: Expression, env: Environment): BlimpObject {

        val a = left.evaluate(env)
        val b = right.evaluate(env)

        return a.type.typeOperator.lesserEqual(a , b)


    }

}