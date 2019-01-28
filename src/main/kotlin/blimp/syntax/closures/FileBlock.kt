package blimp.syntax.closures

import blimp.runtime.BlimpObject
import blimp.runtime.Environment
import blimp.syntax.Node

class FileBlock(children: List<Node>): Block(children) {

    override val canEvaluate = false

    override fun execute(env: Environment) {
        children.forEach {
            it.execute(env)
        }
    }

    override fun evaluate(env: Environment): BlimpObject {
        throw Exception("Cannot evaluate a file block")
    }

}