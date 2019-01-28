package blimp.syntax.statement.statements.ops

import blimp.lex.Token
import blimp.lex.tokens.multichar.IdentifierToken
import blimp.lex.tokens.multichar.Keyword
import blimp.lex.tokens.multichar.KeywordToken
import blimp.runtime.BlimpObject
import blimp.runtime.Environment
import blimp.runtime.typing.Types
import blimp.runtime.typing.types.primitive.*
import blimp.syntax.Node
import blimp.syntax.NodeEmitter
import blimp.syntax.NodeMatcher
import blimp.syntax.statement.Statement

class GetOpStatement(val identifier: String): Statement() {

    companion object Emitter: NodeEmitter() {

        override val matcher = NodeMatcher.create {

            checkTermination = true

            requiredToken {
                it is KeywordToken && it.keyword == Keyword.Get
            }

            requiredToken("identifier") {
                it is IdentifierToken
            }

        }

        override fun getNode(tokens: List<Token>): Node {
            val identifier = matcher.getSingleTokenTags(tokens)["identifier"]

            if (identifier != null && identifier is IdentifierToken) {
                return GetOpStatement(identifier.identifier)
            } else throw Exception("Improper Identifier")
        }

    }

    override fun execute(env: Environment) {
        val obj = env.objects[identifier] ?: throw Exception("Get Op failed, $identifier doesn't exist")

        if (!obj.mutable) {
            throw Exception("Get Op failed, $identifier isn't mutable")
        }

        print("[Input] -> ")
        val input = readLine()!!
        when (obj.type) {
            is FloatType -> {
                val floatVal = input.toFloatOrNull() ?: throw Exception("Get Op failed, $identifier is a Float, input can't be converted")
                env.objects[identifier] = BlimpObject(Types.float, floatVal, true)
            }

            is StringType -> {
                env.objects[identifier] = BlimpObject(Types.string, input, true)
            }

            is IntType -> {
                val intVal = input.toIntOrNull() ?: throw Exception("Get Op failed, $identifier is Int, cannot convert input")
                env.objects[identifier] = BlimpObject(Types.int, intVal, true)
            }

            is BooleanType -> {
                if (input == "true" || input == "false") {
                    val boolVal = (input == "true")
                    env.objects[identifier] = BlimpObject(Types.bool, boolVal, true)
                } else throw Exception("Get Op failed, $identifier is Boolean, cannot process input")
            }

            is CharType -> {
                env.objects[identifier] = BlimpObject(Types.char, input[0], true)
            }

            else -> throw Exception("Get Op failed, $identifier is unimplemented type ${obj.type}")
        }
    }

}