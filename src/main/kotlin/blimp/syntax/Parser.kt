package blimp.syntax

import blimp.lex.Token
import blimp.syntax.closures.ClosureBlock
import blimp.syntax.expression.Expression
import blimp.syntax.statement.statements.assigns.AssignStatement
import blimp.syntax.statement.statements.assigns.CreateAssignStatement
import blimp.syntax.statement.statements.assigns.operators.*
import blimp.syntax.statement.statements.ops.ExecOpStatement
import blimp.syntax.statement.statements.ops.GetOpStatement
import blimp.syntax.statement.statements.ops.PutOpStatement

object Parser {

    private val emitters = arrayOf(

        // Closures
        ClosureBlock.Emitter,

        // Statements
        // OP Statements
        ExecOpStatement.Emitter,
        GetOpStatement.Emitter,
        PutOpStatement.Emitter,

        // Assign Statements
        CreateAssignStatement.Emitter,
        AssignStatement.Emitter,
        AddAssignStatement.Emitter,
        SubtractAssignStatement.Emitter,
        MultiplyAssignStatement.Emitter,
        DivideAssignStatement.Emitter,
        ModuloAssignStatement.Emitter,

        // Expression
        Expression.Emitter

    )

    fun getNodes(tokens: List<Token>): List<Node> {

        var working = tokens
        val nodes = mutableListOf<Node>()

        while (working.isNotEmpty()) {
            val emitter = emitters.find { it.canEmit(working) }
            if (emitter != null) {
                val (newWorking, emittedNode) = emitter.emit(working)
                working = newWorking
                nodes.add(emittedNode)
            } else throw Exception("Failed parsing ($working)")
        }

        return nodes
    }

}