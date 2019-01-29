package blimp.runtime.execution.executors.statement

import blimp.runtime.BlimpObject
import blimp.runtime.Environment
import blimp.runtime.execution.NodeExecutor
import blimp.syntax.Node
import blimp.syntax.statement.Statement

abstract class StatementExecutor<T: Statement>: NodeExecutor<T>() {

    override fun evaluate(n: T, env: Environment): BlimpObject {
        throw Exception("Cannot evaluate a statement")
    }

}