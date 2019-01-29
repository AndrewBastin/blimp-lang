package blimp.runtime.execution.executors.closures

import blimp.runtime.execution.NodeExecutor
import blimp.syntax.closures.Block

abstract class BlockExecutor<T: Block>: NodeExecutor<T>()