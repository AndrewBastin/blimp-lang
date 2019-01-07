package blimp.runtime.typing.types.primitive

import blimp.runtime.BlimpObject
import blimp.runtime.typing.Type
import blimp.runtime.typing.TypeOperator
import blimp.runtime.typing.Types

class TypeType: Type() {

    override val typeName: String = "Type"

    override val typeOperator = object: TypeOperator() {

        override fun add(a: BlimpObject, b: BlimpObject): BlimpObject {
            throw Exception("Cannot add on a Type")
        }

        override fun sub(a: BlimpObject, b: BlimpObject): BlimpObject {
            throw Exception("Cannot subtract on a type")
        }

        override fun mul(a: BlimpObject, b: BlimpObject): BlimpObject {
            throw Exception("Cannot multiply on a type")
        }

        override fun div(a: BlimpObject, b: BlimpObject): BlimpObject {
            throw Exception("Cannot divide on a type")
        }

        override fun mod(a: BlimpObject, b: BlimpObject): BlimpObject {
            throw Exception("Cannot modulo on a type")
        }

        override fun and(a: BlimpObject, b: BlimpObject): BlimpObject {
            throw Exception("Cannot subtract and a type")
        }

        override fun or(a: BlimpObject, b: BlimpObject): BlimpObject {
            throw Exception("Cannot or on a type")
        }

        override fun greater(a: BlimpObject, b: BlimpObject): BlimpObject {
            throw Exception("Cannot greater on a type")
        }

        override fun lesser(a: BlimpObject, b: BlimpObject): BlimpObject {
            throw Exception("Cannot lesser on a type")
        }

        override fun greaterEqual(a: BlimpObject, b: BlimpObject): BlimpObject {
            throw Exception("Cannot greater equal on a type")
        }

        override fun lesserEqual(a: BlimpObject, b: BlimpObject): BlimpObject {
            throw Exception("Cannot lesser equal on a type")
        }

        override fun equal(a: BlimpObject, b: BlimpObject): BlimpObject {
            if (b.type is TypeType) {
                return BlimpObject(Types.bool, a.value == b.value)
            } else throw Exception("Cannot equal on a type with a ${b.type}")
        }

        override fun notEqual(a: BlimpObject, b: BlimpObject): BlimpObject {
            if (b.type is TypeType) {
                return BlimpObject(Types.bool, a.value != b.value)
            } else throw Exception("Cannot equal on a type with a ${b.type}")
        }

        override fun not(a: BlimpObject): BlimpObject {
            throw Exception("Cannot not on a type")
        }

        override fun cast(a: BlimpObject, type: Type): BlimpObject {
            throw Exception("Cannot cast a type")
        }

    }

}