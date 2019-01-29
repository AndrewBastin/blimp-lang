package blimp.runtime.execution

import blimp.runtime.BlimpObject
import blimp.runtime.Environment
import blimp.syntax.Node

abstract class NodeExecutor<T: Node> {

    // exec and eval are the functions used in Executor to prevent type erasure headaches due to generics

    @Suppress("UNCHECKED_CAST")
    fun exec(n: Node, env: Environment) {
        execute(n as T, env)
    }

    @Suppress("UNCHECKED_CAST")
    fun eval(n: Node, env: Environment): BlimpObject {
        return evaluate(n as T, env)
    }

    protected abstract fun evaluate(n: T, env: Environment): BlimpObject
    protected abstract fun execute(n: T, env: Environment)

}