import blimp.lex.Lexer
import blimp.lex.tokens.multichar.*
import blimp.lex.tokens.singlechar.AssignToken
import blimp.lex.tokens.singlechar.BracketToken
import blimp.lex.tokens.singlechar.NotToken
import blimp.syntax.NodeMatcher
import blimp.syntax.expression.Expression
import io.kotlintest.assertSoftly
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class NodeMatcherTests : StringSpec() {

    init {

        "It Works" {

            val matcher = NodeMatcher.create {

                checkTermination = true

                requiredToken {
                    it is KeywordToken && it.keyword == Keyword.Let
                }

                optionalToken {
                    it is KeywordToken && it.keyword == Keyword.Var
                }

                requiredToken {
                    it is IdentifierToken
                }

                requiredToken {
                    it is AssignToken
                }

                considerTokens {
                    it is StringToken || it is NumberToken || it is BooleanToken
                            || it is BinaryOpToken || it is CharToken || it is NotToken || it is BracketToken || it is IdentifierToken
                } takeAll {
                    Expression.matchTokenChain(it)
                }

            }

            assertSoftly {

                matcher.match(
                    Lexer.lex("let a = 10")
                ) shouldBe true

                matcher.match(
                    Lexer.lex("let a = b + c")
                ) shouldBe true


                matcher.match(
                    Lexer.lex("EXEC a")
                ) shouldBe false

                matcher.match(
                    Lexer.lex("let a = (a + b + c / 2) == 4")
                ) shouldBe true

                matcher.match(
                    Lexer.lex("let a = \n(a + b + c / 2) == 4")
                ) shouldBe true

                matcher.match(
                    Lexer.lex("let a = \n;")
                ) shouldBe false

                matcher.match(
                    Lexer.lex("let a = 1 + 1; 5")
                ) shouldBe true

                matcher.match(
                    Lexer.lex("let a = ;")
                ) shouldBe false

                matcher.match(
                    Lexer.lex("let a = \n ;")
                ) shouldBe false
            }

        }

    }

}