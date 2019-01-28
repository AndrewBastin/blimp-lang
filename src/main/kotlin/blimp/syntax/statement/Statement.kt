package blimp.syntax.statement

import blimp.runtime.BlimpObject
import blimp.runtime.Environment
import blimp.syntax.Node

abstract class Statement: Node() {

    override val canEvaluate = false
    override val isClosure = false

    // Executes the statement
    abstract override fun execute(env: Environment)

    override fun evaluate(env: Environment): BlimpObject {
        throw Exception("Cannot evaluate a statement")
    }

}