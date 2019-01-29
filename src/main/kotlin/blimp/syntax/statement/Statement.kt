package blimp.syntax.statement

import blimp.syntax.Node

abstract class Statement: Node() {

    override val canEvaluate = false
    override val isClosure = false

}