package blimp.runtime.typing.types.primitive

import blimp.runtime.BlimpObject
import blimp.runtime.typing.Type
import blimp.runtime.typing.TypeOperator
import blimp.runtime.typing.Types

class CharType: Type() {

    override val typeName: String = "Char"

    override val typeOperator = object:TypeOperator() {

        override fun cast(a: BlimpObject, type: Type): BlimpObject {

            return when (type) {

                is CharType -> BlimpObject(Types.char, a.value as Char)
                is StringType -> BlimpObject(Types.string, a.value as String)
                is IntType -> BlimpObject(Types.int, (a.value as Char).toInt())

                else -> throw Exception("Cannot cast a Char to a ${type.typeName}")
            }

        }

        override fun add(a: BlimpObject, b: BlimpObject): BlimpObject {
            return when (b.type) {

                is CharType -> {
                    BlimpObject(Types.string, "${a.value as Char}${b.value as Char}")
                }

                is StringType -> {
                    BlimpObject(Types.string, "${a.value as Char}${b.value as String}")
                }

                else -> {
                    throw Exception("Cannot add a Char with a ${b.type.typeName}")
                }

            }
        }

        override fun sub(a: BlimpObject, b: BlimpObject): BlimpObject {
            throw Exception("Cannot subtract a Char with a ${b.type.typeName}")
        }

        override fun mul(a: BlimpObject, b: BlimpObject): BlimpObject {
            throw Exception("Cannot multiply a Char with a ${b.type.typeName}")
        }

        override fun div(a: BlimpObject, b: BlimpObject): BlimpObject {
            throw Exception("Cannot divide a Char with a ${b.type.typeName}")
        }

        override fun mod(a: BlimpObject, b: BlimpObject): BlimpObject {
            throw Exception("Cannot mod a Char with a ${b.type.typeName}")
        }

        override fun and(a: BlimpObject, b: BlimpObject): BlimpObject {
            throw Exception("Cannot and a Char with a ${b.type.typeName}")
        }

        override fun or(a: BlimpObject, b: BlimpObject): BlimpObject {
            throw Exception("Cannot or a Char with a ${b.type.typeName}")
        }

        override fun greater(a: BlimpObject, b: BlimpObject): BlimpObject {
            if (b.type is CharType) {
                return BlimpObject(Types.bool, (a.value as Char) > (b.value as Char))
            } else throw Exception("Cannot greater a Char with a ${b.type.typeName}")
        }

        override fun lesser(a: BlimpObject, b: BlimpObject): BlimpObject {
            if (b.type is CharType) {
                return BlimpObject(Types.bool, (a.value as Char) < (b.value as Char))
            } else throw Exception("Cannot lesser a Char with a ${b.type.typeName}")
        }

        override fun greaterEqual(a: BlimpObject, b: BlimpObject): BlimpObject {
            if (b.type is CharType) {
                return BlimpObject(Types.bool, (a.value as Char) >= (b.value as Char))
            } else throw Exception("Cannot greater equal a Char with a ${b.type.typeName}")
        }

        override fun lesserEqual(a: BlimpObject, b: BlimpObject): BlimpObject {
            if (b.type is CharType) {
                return BlimpObject(Types.bool, (a.value as Char) <= (b.value as Char))
            } else throw Exception("Cannot lesser equal a Char with a ${b.type.typeName}")
        }

        override fun equal(a: BlimpObject, b: BlimpObject): BlimpObject {
            if (b.type is CharType) {
                return BlimpObject(Types.bool, (a.value as Char) == (b.value as Char))
            } else throw Exception("Cannot equal a Char with a ${b.type.typeName}")
        }

        override fun notEqual(a: BlimpObject, b: BlimpObject): BlimpObject {
            if (b.type is CharType) {
                return BlimpObject(Types.bool, (a.value as Char) != (b.value as Char))
            } else throw Exception("Cannot not equal a Char with a ${b.type.typeName}")
        }

        override fun not(a: BlimpObject): BlimpObject {
            throw Exception("Cannot not not a Char")
        }

    }

}