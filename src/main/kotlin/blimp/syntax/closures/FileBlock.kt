package blimp.syntax.closures

import blimp.syntax.Node

class FileBlock(children: List<Node>): Block(children) {

    override val canEvaluate = false
}