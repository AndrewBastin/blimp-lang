package blimp.syntax.statement.statements.ops

import blimp.lex.Lexer
import blimp.lex.Token
import blimp.lex.tokens.multichar.Keyword
import blimp.lex.tokens.multichar.KeywordToken
import blimp.lex.tokens.multichar.StringToken
import blimp.runtime.Environment
import blimp.syntax.Node
import blimp.syntax.NodeEmitter
import blimp.syntax.NodeMatcher
import blimp.syntax.Parser
import blimp.syntax.closures.FileBlock
import blimp.syntax.statement.Statement
import blimp.syntax.statement.StatementProvider
import blimp.syntax.statement.validation.StatementValidator
import java.io.File
import java.lang.Exception
import kotlin.system.measureTimeMillis

data class ExecOpStatement(val filePath: String): Statement() {

    companion object Emitter: NodeEmitter() {

        override val matcher = NodeMatcher.create {

            checkTermination = true

            requiredToken {
                it is KeywordToken && it.keyword == Keyword.Exec
            }

            requiredToken("execPath") {
                it is StringToken
            }

        }

        override fun getNode(tokens: List<Token>): Node {
            val path = matcher.getSingleTokenTags(tokens)["execPath"]

            if (path != null && path is StringToken) {
                return ExecOpStatement(path.value)
            } else throw Exception("Improper exec path")
        }

    }

    override fun execute(env: Environment) {

        val time = measureTimeMillis {
            FileBlock(
                Parser.getNodes(
                    Lexer.lex(
                        File(filePath).readText()
                    )
                )
            ).execute(env)
        }

        println("\nCompleted Execution in $time ms")
    }


}