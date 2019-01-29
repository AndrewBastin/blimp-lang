package blimp.runtime.execution.executors.closures

import blimp.runtime.BlimpObject
import blimp.runtime.Environment
import blimp.runtime.Executor
import blimp.syntax.closures.REPLBlock

object REPLBlockExecutor: BlockExecutor<REPLBlock>() {

    override fun evaluate(n: REPLBlock, env: Environment): BlimpObject {
        if (!n.canEvaluate) throw Exception("Cannot evaluate this block. Check with the canEvaluate property")

        n.children.forEachIndexed { index, node ->
            if (index == n.children.size - 1) {
                return Executor.evaluate(node, env)
            } else Executor.execute(node, env)
        }

        throw Exception("Didn't return the last evaluation")
    }

    override fun execute(n: REPLBlock, env: Environment) {
        n.children.forEach {
            Executor.execute(it, env)
        }
    }

}