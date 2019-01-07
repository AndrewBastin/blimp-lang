package blimp.runtime.typing.types.primitive

import blimp.runtime.BlimpObject
import blimp.runtime.typing.Type
import blimp.runtime.typing.TypeOperator
import blimp.runtime.typing.Types

class IntType: Type() {

    override val typeName: String = "Int"

    override val typeOperator = object: TypeOperator() {

        override fun cast(a: BlimpObject, type: Type): BlimpObject {

            return when (type) {

                is IntType -> BlimpObject(Types.int, a.value as Int)
                is StringType -> BlimpObject(Types.string, (a.value as Int).toString())
                is FloatType -> BlimpObject(Types.float, (a.value as Int).toFloat())

                else -> throw Exception("Cannot cast Int to ${type.typeName}")

            }

        }

        override fun not(a: BlimpObject): BlimpObject {
            throw Exception("Cannot not an Integer")
        }

        override fun notEqual(a: BlimpObject, b: BlimpObject): BlimpObject {
            return when (b.type) {

                is IntType -> BlimpObject(Types.bool, (a.value as Int) != (b.value as Int))
                is FloatType -> BlimpObject(Types.bool, (a.value as Int).toFloat() != (b.value as Float))

                else -> throw Exception("Cannot equate a Int with ${b.type.typeName}")
            }
        }

        override fun equal(a: BlimpObject, b: BlimpObject): BlimpObject {
            return when (b.type) {

                is IntType -> BlimpObject(Types.bool, (a.value as Int) == (b.value as Int))
                is FloatType -> BlimpObject(Types.bool, (a.value as Int).toFloat() == (b.value as Float))

                else -> throw Exception("Cannot equate a Int with ${b.type.typeName}")
            }
        }

        override fun greater(a: BlimpObject, b: BlimpObject): BlimpObject {
            return when (b.type) {

                is IntType -> BlimpObject(Types.bool, (a.value as Int) > (b.value as Int))
                is FloatType -> BlimpObject(Types.bool, (a.value as Int) > (b.value as Float))

                else -> throw Exception("Cannot greater than on a Int with ${b.type.typeName}")

            }
        }

        override fun lesser(a: BlimpObject, b: BlimpObject): BlimpObject {
            return when (b.type) {

                is IntType -> BlimpObject(Types.bool, (a.value as Int) < (b.value as Int))
                is FloatType -> BlimpObject(Types.bool, (a.value as Int) < (b.value as Float))

                else -> throw Exception("Cannot lesser than on a Int with ${b.type.typeName}")

            }
        }

        override fun greaterEqual(a: BlimpObject, b: BlimpObject): BlimpObject {
            return when (b.type) {

                is IntType -> BlimpObject(Types.bool, (a.value as Int) >= (b.value as Int))
                is FloatType -> BlimpObject(Types.bool, (a.value as Int) >= (b.value as Float))

                else -> throw Exception("Cannot greater than or equal to on a Int with ${b.type.typeName}")

            }
        }

        override fun lesserEqual(a: BlimpObject, b: BlimpObject): BlimpObject {
            return when (b.type) {

                is IntType -> BlimpObject(Types.bool, (a.value as Int) <= (b.value as Int))
                is FloatType -> BlimpObject(Types.bool, (a.value as Int) <= (b.value as Float))

                else -> throw Exception("Cannot lesser than or equal to on a Int with ${b.type.typeName}")

            }
        }

        override fun and(a: BlimpObject, b: BlimpObject): BlimpObject {
            throw Exception("Cannot and a Int with ${b.type.typeName}")
        }

        override fun or(a: BlimpObject, b: BlimpObject): BlimpObject {
            throw Exception("Cannot or a Int with ${b.type.typeName}")
        }

        override fun add(a: BlimpObject, b: BlimpObject): BlimpObject {
            return when (b.type) {
                is FloatType -> BlimpObject(Types.float, (a.value as Int) + (b.value as Float))
                is IntType -> BlimpObject(Types.int, (a.value as Int) + (b.value as Int))
                else -> throw Exception("Cannot add a Int with ${b.type.typeName}")
            }
        }

        override fun sub(a: BlimpObject, b: BlimpObject): BlimpObject {
            return when (b.type) {
                is FloatType -> BlimpObject(Types.float, (a.value as Int) - (b.value as Float))
                is IntType -> BlimpObject(Types.int, (a.value as Int) - (b.value as Int))
                else -> throw Exception("Cannot subtract a Int with ${b.type.typeName}")
            }
        }

        override fun mul(a: BlimpObject, b: BlimpObject): BlimpObject {
            return when (b.type) {
                is FloatType -> BlimpObject(Types.float, (a.value as Int) * (b.value as Float))
                is IntType -> BlimpObject(Types.int, (a.value as Int) * (b.value as Int))
                else -> throw Exception("Cannot multiply a Int with ${b.type.typeName}")
            }
        }

        override fun div(a: BlimpObject, b: BlimpObject): BlimpObject {
            return when (b.type) {
                is FloatType -> BlimpObject(Types.float, (a.value as Int) / (b.value as Float))
                is IntType -> BlimpObject(Types.int, (a.value as Int) / (b.value as Int))
                else -> throw Exception("Cannot divide a Int with ${b.type.typeName}")
            }
        }

        override fun mod(a: BlimpObject, b: BlimpObject): BlimpObject {
            return when (b.type) {
                is FloatType -> BlimpObject(Types.float, (a.value as Int) % (b.value as Float))
                is IntType -> BlimpObject(Types.int, (a.value as Int) % (b.value as Int))
                else -> throw Exception("Cannot modulo a Int with ${b.type.typeName}")
            }
        }

    }

}