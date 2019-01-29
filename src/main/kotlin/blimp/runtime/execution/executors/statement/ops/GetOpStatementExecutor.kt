package blimp.runtime.execution.executors.statement.ops

import blimp.runtime.BlimpObject
import blimp.runtime.Environment
import blimp.runtime.execution.executors.statement.StatementExecutor
import blimp.runtime.typing.Types
import blimp.runtime.typing.types.primitive.*
import blimp.syntax.statement.statements.ops.GetOpStatement

object GetOpStatementExecutor: StatementExecutor<GetOpStatement>() {
    override fun execute(n: GetOpStatement, env: Environment) {
        val obj = env.objects[n.identifier] ?: throw Exception("Get Op failed, ${n.identifier} doesn't exist")

        if (!obj.mutable) {
            throw Exception("Get Op failed, ${n.identifier} isn't mutable")
        }

        print("[Input] -> ")
        val input = readLine()!!
        when (obj.type) {
            is FloatType -> {
                val floatVal = input.toFloatOrNull() ?: throw Exception("Get Op failed, ${n.identifier} is a Float, input can't be converted")
                env.objects[n.identifier] = BlimpObject(Types.float, floatVal, true)
            }

            is StringType -> {
                env.objects[n.identifier] = BlimpObject(Types.string, input, true)
            }

            is IntType -> {
                val intVal = input.toIntOrNull() ?: throw Exception("Get Op failed, ${n.identifier} is Int, cannot convert input")
                env.objects[n.identifier] = BlimpObject(Types.int, intVal, true)
            }

            is BooleanType -> {
                if (input == "true" || input == "false") {
                    val boolVal = (input == "true")
                    env.objects[n.identifier] = BlimpObject(Types.bool, boolVal, true)
                } else throw Exception("Get Op failed, ${n.identifier} is Boolean, cannot process input")
            }

            is CharType -> {
                env.objects[n.identifier] = BlimpObject(Types.char, input[0], true)
            }

            else -> throw Exception("Get Op failed, ${n.identifier} is unimplemented type ${obj.type}")
        }
    }

}