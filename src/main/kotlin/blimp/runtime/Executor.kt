package blimp.runtime

import blimp.runtime.execution.executors.closures.FileBlockExecutor
import blimp.runtime.execution.executors.closures.REPLBlockExecutor
import blimp.runtime.execution.executors.expression.*
import blimp.runtime.execution.executors.statement.assigns.AssignStatementExecutor
import blimp.runtime.execution.executors.statement.assigns.CreateAssignStatementExecutor
import blimp.runtime.execution.executors.statement.assigns.operators.*
import blimp.runtime.execution.executors.statement.ops.ExecOpStatementExecutor
import blimp.runtime.execution.executors.statement.ops.GetOpStatementExecutor
import blimp.runtime.execution.executors.statement.ops.PutOpStatementExecutor
import blimp.syntax.Node
import blimp.syntax.closures.FileBlock
import blimp.syntax.closures.REPLBlock
import blimp.syntax.expression.*
import blimp.syntax.statement.statements.assigns.AssignStatement
import blimp.syntax.statement.statements.assigns.CreateAssignStatement
import blimp.syntax.statement.statements.assigns.operators.*
import blimp.syntax.statement.statements.ops.ExecOpStatement
import blimp.syntax.statement.statements.ops.GetOpStatement
import blimp.syntax.statement.statements.ops.PutOpStatement

object Executor {

    private val executors = mapOf(
        FileBlock::class to FileBlockExecutor,
        REPLBlock::class to REPLBlockExecutor,

        BinaryExpression::class to BinaryExpressionExecutor,
        GroupingExpression::class to GroupingExpressionExecutor,
        IdentifierExpression::class to IdentifierExpressionExecutor,
        LiteralExpression::class to LiteralExpressionExecutor,
        UnaryExpression::class to UnaryExpressionExecutor,

        AddAssignStatement::class to AddAssignStatementExecutor,
        DivideAssignStatement::class to DivideAssignStatementExecutor,
        ModuloAssignStatement::class to ModuloAssignStatementExecutor,
        MultiplyAssignStatement::class to MultiplyAssignStatementExecutor,
        SubtractAssignStatement::class to SubtractAssignStatementExecutor,

        AssignStatement::class to AssignStatementExecutor,
        CreateAssignStatement::class to CreateAssignStatementExecutor,

        ExecOpStatement::class to ExecOpStatementExecutor,
        GetOpStatement::class to GetOpStatementExecutor,
        PutOpStatement::class to PutOpStatementExecutor
    )

    fun execute(n: Node, env: Environment) {
        val executor = executors[n::class]

        if (executor != null) {
            executor.exec(n, env)
        } else throw Exception("Unknown executor for node type '${n::class}'")
    }

    fun evaluate(n: Node, env: Environment): BlimpObject {
        val executor = executors[n::class]

        if (executor != null) {
            return executor.eval(n, env)
        } else throw Exception("Unknow executor for node type '${n::class}'")
    }

}