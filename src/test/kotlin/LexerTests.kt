import blimp.lex.Lexer
import blimp.lex.tokens.singlechar.BracketToken
import blimp.lex.tokens.singlechar.BracketType
import io.kotlintest.assertSoftly
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class LexerTests : StringSpec() {

    init {

        "Proper Tokenization" {

            assertSoftly {

                Lexer.lex("(") shouldBe listOf(BracketToken(BracketType.Opening))
                Lexer.lex(")") shouldBe listOf(BracketToken(BracketType.Closing))

                // TODO : Complete this
            }

        }

    }

}