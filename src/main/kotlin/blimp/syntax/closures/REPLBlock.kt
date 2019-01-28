package blimp.syntax.closures

import blimp.runtime.BlimpObject
import blimp.runtime.Environment
import blimp.syntax.Node

class REPLBlock(children: List<Node>): Block(children) {

    override val canEvaluate = children.last().canEvaluate

    override fun execute(env: Environment) {
        children.forEach {
            it.execute(env)
        }
    }

    override fun evaluate(env: Environment): BlimpObject {

        if (!canEvaluate) throw Exception("Cannot evaluate this block. Check with the canEvaluate property")

        children.forEachIndexed { index, node ->
            if (index == children.size - 1 && canEvaluate) {
                return node.evaluate(env)
            } else node.execute(env)
        }

        throw Exception("Didn't return the last evaluation")
    }


}