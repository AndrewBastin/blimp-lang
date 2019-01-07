package blimp.syntax.statement.statements.ops

import blimp.lex.Lexer
import blimp.lex.Token
import blimp.lex.tokens.multichar.Keyword
import blimp.lex.tokens.multichar.KeywordToken
import blimp.lex.tokens.multichar.StringToken
import blimp.runtime.Environment
import blimp.syntax.TokenProcessor
import blimp.syntax.statement.Statement
import blimp.syntax.statement.StatementProvider
import blimp.syntax.statement.validation.StatementValidator
import java.io.File
import kotlin.system.measureTimeMillis

class ExecOpStatement(val filePath: String): Statement() {

    companion object : StatementProvider<ExecOpStatement>() {

        override fun matchTokenChain(tokens: List<Token>) = StatementValidator.validate(tokens) {

            requiredToken {
                it is KeywordToken && it.keyword == Keyword.Exec
            }

            requiredToken {
                it is StringToken
            }

        }

        override fun create(tokens: List<Token>): ExecOpStatement {

            var filePath: String? = null

            StatementValidator.validate(tokens) {

                requiredToken {
                    it is KeywordToken
                }

                requiredToken {
                    if (it is StringToken) {
                        filePath = it.value

                        true
                    } else false
                }

            }

            return ExecOpStatement(filePath ?: throw Exception("Exec op failed, file path came null"))
        }

    }


    override fun execute(env: Environment) {

        val time = measureTimeMillis {
            File(filePath).readLines().forEach {
                TokenProcessor.getStatementFromTokenChain(
                    Lexer.lex(it)
                ).execute(env)
            }
        }

        println("\nCompleted Execution in $time ms")
    }


}