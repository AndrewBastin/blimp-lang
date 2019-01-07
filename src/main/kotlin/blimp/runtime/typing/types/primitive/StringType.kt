package blimp.runtime.typing.types.primitive

import blimp.runtime.BlimpObject
import blimp.runtime.typing.Type
import blimp.runtime.typing.TypeOperator
import blimp.runtime.typing.Types

class StringType: Type() {

    override val typeName: String = "String"

    override val typeOperator = object:TypeOperator() {

        override fun cast(a: BlimpObject, type: Type): BlimpObject {
            return when (type) {

                is StringType -> BlimpObject(Types.string, a.value as String)

                else -> throw Exception("Cannot cast String to a ${type.typeName}")
            }
        }

        override fun add(a: BlimpObject, b: BlimpObject): BlimpObject {
            when (b.type) {

                is CharType -> return BlimpObject(Types.string, (a.value as String) + (b.value as Char))
                is StringType -> return BlimpObject(Types.string, (a.value as String) + (b.value as String))

                else -> throw Exception("Cannot add a String with a ${b.type.typeName}")
            }
        }

        override fun sub(a: BlimpObject, b: BlimpObject): BlimpObject {
            throw Exception("Cannot subtract a String with a ${b.type.typeName}")
        }

        override fun mul(a: BlimpObject, b: BlimpObject): BlimpObject {
            throw Exception("Cannot multiply a String with a ${b.type.typeName}")
        }

        override fun div(a: BlimpObject, b: BlimpObject): BlimpObject {
            throw Exception("Cannot divide a String with a ${b.type.typeName}")
        }

        override fun mod(a: BlimpObject, b: BlimpObject): BlimpObject {
            throw Exception("Cannot mod a String with a ${b.type.typeName}")
        }

        override fun and(a: BlimpObject, b: BlimpObject): BlimpObject {
            throw Exception("Cannot and a String with a ${b.type.typeName}")
        }

        override fun or(a: BlimpObject, b: BlimpObject): BlimpObject {
            throw Exception("Cannot or a String with a ${b.type.typeName}")
        }

        override fun greater(a: BlimpObject, b: BlimpObject): BlimpObject {
            if (b.type is StringType) {
                return BlimpObject(Types.bool, (a.value as String) > (b.value as String))
            } else throw Exception("Cannot greater a String with a ${b.type.typeName}")
        }

        override fun lesser(a: BlimpObject, b: BlimpObject): BlimpObject {
            if (b.type is StringType) {
                return BlimpObject(Types.bool, (a.value as String) < (b.value as String))
            } else throw Exception("Cannot lesser a String with a ${b.type.typeName}")}

        override fun greaterEqual(a: BlimpObject, b: BlimpObject): BlimpObject {
            if (b.type is StringType) {
                return BlimpObject(Types.bool, (a.value as String) >= (b.value as String))
            } else throw Exception("Cannot greater equal a String with a ${b.type.typeName}")
        }

        override fun lesserEqual(a: BlimpObject, b: BlimpObject): BlimpObject {
            if (b.type is StringType) {
                return BlimpObject(Types.bool, (a.value as String) <= (b.value as String))
            } else throw Exception("Cannot lesser equal a String with a ${b.type.typeName}")}

        override fun equal(a: BlimpObject, b: BlimpObject): BlimpObject {
            if (b.type is StringType) {
                return BlimpObject(Types.bool, (a.value as String) == (b.value as String))
            } else throw Exception("Cannot equal a String with a ${b.type.typeName}")
        }

        override fun notEqual(a: BlimpObject, b: BlimpObject): BlimpObject {
            if (b.type is StringType) {
                return BlimpObject(Types.bool, (a.value as String) != (b.value as String))
            } else throw Exception("Cannot not equal a String with a ${b.type.typeName}")
        }

        override fun not(a: BlimpObject): BlimpObject {
            throw Exception("Cannot not a String")
        }

    }
}