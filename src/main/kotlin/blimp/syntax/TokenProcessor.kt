package blimp.syntax

import blimp.lex.Token
import blimp.syntax.statement.statements.assigns.AssignStatement
import blimp.syntax.statement.statements.assigns.CreateAssignStatement
import blimp.syntax.statement.statements.ExpressionStatement
import blimp.syntax.statement.Statement
import blimp.syntax.statement.statements.assigns.operators.*
import blimp.syntax.statement.statements.ops.ExecOpStatement
import blimp.syntax.statement.statements.ops.GetOpStatement
import blimp.syntax.statement.statements.ops.PutOpStatement

object TokenProcessor {

    // Tries to convert a token chain to a statement
    fun getStatementFromTokenChain(tokens: List<Token>): Statement = when {

        ExecOpStatement.matchTokenChain(tokens) -> ExecOpStatement.create(tokens)
        GetOpStatement.matchTokenChain(tokens) -> GetOpStatement.create(tokens)
        PutOpStatement.matchTokenChain(tokens) -> PutOpStatement.create(tokens)
        ExpressionStatement.matchTokenChain(tokens) -> ExpressionStatement.create(tokens)
        CreateAssignStatement.matchTokenChain(tokens) -> CreateAssignStatement.create(tokens)
        AssignStatement.matchTokenChain(tokens) -> AssignStatement.create(tokens)
        AddAssignStatement.matchTokenChain(tokens) -> AddAssignStatement.create(tokens)
        SubtractAssignStatement.matchTokenChain(tokens) -> SubtractAssignStatement.create(tokens)
        MultiplyAssignStatement.matchTokenChain(tokens) -> MultiplyAssignStatement.create(tokens)
        DivideAssignStatement.matchTokenChain(tokens) -> DivideAssignStatement.create(tokens)
        ModuloAssignStatement.matchTokenChain(tokens) -> ModuloAssignStatement.create(tokens)


        else -> throw Exception("Couldn't convert token chain into a statement")

    }

}