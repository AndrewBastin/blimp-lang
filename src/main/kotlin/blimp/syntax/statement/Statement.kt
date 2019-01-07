package blimp.syntax.statement

import blimp.runtime.Environment
import blimp.syntax.SyntaxElement

// Statements do not have a core value, rather are just a pattern of arranging tokens/expressions
abstract class Statement: SyntaxElement() {

    // Executes the statement
    abstract fun execute(env: Environment)

}