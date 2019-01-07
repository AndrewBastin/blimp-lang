package blimp.runtime.typing.types.primitive

import blimp.runtime.BlimpObject
import blimp.runtime.typing.Type
import blimp.runtime.typing.TypeOperator
import blimp.runtime.typing.Types


class BooleanType: Type() {

    override val typeName = "Boolean"

    override val typeOperator = object:TypeOperator() {

        override fun cast(a: BlimpObject, type: Type): BlimpObject {

            return when (type) {

                is BooleanType -> BlimpObject(Types.bool, a.value as Boolean)
                is StringType -> BlimpObject(Types.string, (a.value as Boolean).toString())

                else -> throw Exception("Cannot cast a Boolean to a ${type.typeName}")
            }
        }

        override fun not(a: BlimpObject): BlimpObject {
            return BlimpObject(Types.bool, !(a.value as Boolean))
        }

        override fun notEqual(a: BlimpObject, b: BlimpObject): BlimpObject {
            if (b.type is BooleanType) {
                return BlimpObject(Types.bool, (a.value as Boolean) != (b.value as Boolean))
            } else throw Exception("Cannot equate a Boolean with ${b.type.typeName}")
        }

        override fun equal(a: BlimpObject, b: BlimpObject): BlimpObject {
            if (b.type is BooleanType) {
                return BlimpObject(Types.bool, (a.value as Boolean) == (b.value as Boolean))
            } else throw Exception("Cannot equate a Boolean with ${b.type.typeName}")
        }

        override fun greater(a: BlimpObject, b: BlimpObject): BlimpObject {
            throw Exception("Cannot perform greater than on a Boolean with ${b.type.typeName}")
        }

        override fun lesser(a: BlimpObject, b: BlimpObject): BlimpObject {
            throw Exception("Cannot perform lesser than on a Boolean with ${b.type.typeName}")
        }

        override fun greaterEqual(a: BlimpObject, b: BlimpObject): BlimpObject {
            throw Exception("Cannot perform greater than or equal to on a Boolean with ${b.type.typeName}")
        }

        override fun lesserEqual(a: BlimpObject, b: BlimpObject): BlimpObject {
            throw Exception("Cannot perform lesser than or equal to on a Boolean with ${b.type.typeName}")
        }

        override fun and(a: BlimpObject, b: BlimpObject): BlimpObject {
            if (b.type is BooleanType) {
                return BlimpObject(Types.bool, (a.value as Boolean) && (b.value as Boolean))
            } else throw Exception("Cannot and a Boolean with ${b.type.typeName}")
        }

        override fun or(a: BlimpObject, b: BlimpObject): BlimpObject {
            if (b.type is BooleanType) {
                return BlimpObject(Types.bool, (a.value as Boolean) || (b.value as Boolean))
            } else throw Exception("Cannot or a Boolean with ${b.type.typeName}")
        }

        override fun add(a: BlimpObject, b: BlimpObject): BlimpObject {
            throw Exception("Cannot add a Boolean with ${b.type.typeName}")
        }

        override fun sub(a: BlimpObject, b: BlimpObject): BlimpObject {
            throw Exception("Cannot subtract a Boolean with ${b.type.typeName}")
        }

        override fun mul(a: BlimpObject, b: BlimpObject): BlimpObject {
            throw Exception("Cannot multiply a Boolean with ${b.type.typeName}")
        }

        override fun div(a: BlimpObject, b: BlimpObject): BlimpObject {
            throw Exception("Cannot divide a Boolean with ${b.type.typeName}")
        }

        override fun mod(a: BlimpObject, b: BlimpObject): BlimpObject {
            throw Exception("Cannot modulo a Boolean with ${b.type.typeName}")
        }

    }

}