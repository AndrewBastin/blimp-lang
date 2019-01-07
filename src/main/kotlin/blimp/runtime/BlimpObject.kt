package blimp.runtime

import blimp.runtime.typing.Type
import blimp.runtime.typing.Types

data class BlimpObject(
    val type: Type,
    val value: Any,
    val mutable: Boolean = false
) {

    constructor(float: Float): this(Types.float, float)
    constructor(int: Int): this(Types.int, int)

    override fun toString() = value.toString()
}