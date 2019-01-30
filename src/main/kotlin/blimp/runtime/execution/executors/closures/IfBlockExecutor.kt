package blimp.runtime.execution.executors.closures

import blimp.runtime.BlimpObject
import blimp.runtime.Environment
import blimp.runtime.Executor
import blimp.runtime.typing.types.primitive.BooleanType
import blimp.syntax.closures.IfBlock

object IfBlockExecutor: BlockExecutor<IfBlock>() {

    override fun evaluate(n: IfBlock, env: Environment): BlimpObject {
        throw Exception("Cannot evaluate an If block")
    }

    override fun execute(n: IfBlock, env: Environment) {
        val conditionResult = Executor.evaluate(n.condition, env)

        if (conditionResult.type is BooleanType) {

            if ((conditionResult.value as Boolean)) {

                val execEnv = env.spawnChild()
                n.children.forEach {
                    Executor.execute(it, execEnv)
                }
            }

        } else throw Exception("The condition in If block should return a Boolean, here it returns a '${conditionResult.type.typeName}'")
    }

}