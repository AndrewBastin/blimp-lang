package blimp.syntax.closures

import blimp.syntax.Node

abstract class Block(val children: List<Node>): Node() {

    override val isClosure = true

}