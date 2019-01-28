import blimp.lex.Lexer
import blimp.syntax.statement.statements.ops.ExecOpStatement
import io.kotlintest.assertSoftly
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class ExecOpStatementTest : StringSpec() {

    init {

        val matcher = ExecOpStatement.Emitter.matcher

        "Matcher matches correctly" {


            assertSoftly {

                matcher.match(
                    Lexer.lex("")
                ) shouldBe false

                matcher.match(
                    Lexer.lex("EXEC")
                ) shouldBe false

                matcher.match(
                    Lexer.lex("EXEC;")
                ) shouldBe false

                matcher.match(
                    Lexer.lex("EXEC \"This is a test string\"")
                ) shouldBe true

                matcher.match(
                    Lexer.lex("EXEC \n\"This is a test string\"")
                ) shouldBe true

                matcher.match(
                    Lexer.lex("EXEC; \n\"This is again a test\"")
                ) shouldBe false

                matcher.match(
                    Lexer.lex("EXEC \n\"One more test\" \"Exactly, just one more\"")
                ) shouldBe false

                matcher.match(
                    Lexer.lex("EXEC \n\"One more test\" \n\"Exactly, just one more\"")
                ) shouldBe true

                matcher.match(
                    Lexer.lex("EXEC \n\"The last test\"; EXEC \"I promise\"")
                ) shouldBe true

            }

        }

        "Matcher Emits correctly" {

            ExecOpStatement.Emitter.emit(
                Lexer.lex("EXEC \"test\"")
            ).second.apply {
                assertSoftly {

                    (this is ExecOpStatement) shouldBe true
                    if (this is ExecOpStatement) (this.filePath == "test") shouldBe true

                }
            }

        }

    }

}