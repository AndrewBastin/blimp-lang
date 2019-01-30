package blimp.runtime.execution.executors.closures

import blimp.runtime.BlimpObject
import blimp.runtime.Environment
import blimp.runtime.Executor
import blimp.syntax.closures.ClosureBlock

object ClosureBlockExecutor: BlockExecutor<ClosureBlock>() {

    override fun evaluate(n: ClosureBlock, env: Environment): BlimpObject {
        throw Exception("Cannot evaluate a closure")
    }

    override fun execute(n: ClosureBlock, env: Environment) {
        val workEnv = env.spawnChild() // Closures spawn child envs
        n.children.forEach {
            Executor.execute(it, workEnv)
        }
    }

}