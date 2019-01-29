package blimp.runtime.execution.executors.expression.operators

import blimp.runtime.BlimpObject
import blimp.runtime.Environment
import blimp.runtime.Executor
import blimp.runtime.typing.Type
import blimp.runtime.typing.Types
import blimp.runtime.typing.types.primitive.TypeType
import blimp.syntax.expression.Expression

object AsOperator: BinaryOperator() {

    override fun evaluate(left: Expression, right: Expression, env: Environment): BlimpObject {
        val a = Executor.evaluate(left, env)
        val b = Executor.evaluate(right, env)

        if (b.type is TypeType) {

            return a.type.typeOperator.cast(a, b.value as Type)

        } else throw Exception("'as' operator expects a type on the right hand side...")
    }

}


object IsOperator: BinaryOperator() {

    override fun evaluate(left: Expression, right: Expression, env: Environment): BlimpObject {
        val a = Executor.evaluate(left, env)
        val b = Executor.evaluate(right, env)

        if (b.type is TypeType) {

            return BlimpObject(Types.bool, a.type == (b.value as Type))

        } else throw Exception("'as' operator expects a type on the right hand side...")
    }

}