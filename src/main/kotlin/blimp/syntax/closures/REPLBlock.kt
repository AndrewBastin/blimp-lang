package blimp.syntax.closures

import blimp.syntax.Node

class REPLBlock(children: List<Node>): Block(children) {

    override val canEvaluate = children.isNotEmpty() && children.last().canEvaluate

}