package blimp.syntax

import blimp.runtime.BlimpObject
import blimp.runtime.Environment


abstract class Node {

    abstract val canEvaluate: Boolean
    abstract val isClosure: Boolean

    abstract fun execute(env: Environment)
    abstract fun evaluate(env: Environment): BlimpObject

}