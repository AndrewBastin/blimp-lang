package blimp.runtime.execution.executors.closures

import blimp.runtime.BlimpObject
import blimp.runtime.Environment
import blimp.runtime.Executor
import blimp.syntax.closures.FileBlock

object FileBlockExecutor: BlockExecutor<FileBlock>() {
    override fun evaluate(n: FileBlock, env: Environment): BlimpObject {
        throw Exception("Cannot evaluate a file block")
    }

    override fun execute(n: FileBlock, env: Environment) {
        n.children.forEach {
            Executor.execute(it, env)
        }
    }
}