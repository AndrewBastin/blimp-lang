package blimp.runtime.execution.executors.expression.operators

import blimp.runtime.BlimpObject
import blimp.runtime.Environment
import blimp.syntax.expression.Expression

abstract class Operator

abstract class BinaryOperator: Operator() {
    abstract fun evaluate(left: Expression, right: Expression, env: Environment): BlimpObject
}

abstract class UnaryOperator: Operator() {
    abstract fun evaluate(expr: Expression, env: Environment): BlimpObject
}