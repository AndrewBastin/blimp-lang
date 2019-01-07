package blimp.runtime.typing.types.primitive

import blimp.runtime.BlimpObject
import blimp.runtime.typing.Type
import blimp.runtime.typing.TypeOperator
import blimp.runtime.typing.Types
import kotlin.math.roundToInt

class FloatType: Type() {

    override val typeName: String = "Float"

    override val typeOperator = object: TypeOperator() {

        override fun cast(a: BlimpObject, type: Type): BlimpObject {

            return when (type) {

                is FloatType -> BlimpObject(Types.float, a.value as Float)
                is IntType -> BlimpObject(Types.int, (a.value as Float).roundToInt())
                is StringType -> BlimpObject(Types.string, (a.value as Float).toString())

                else -> throw Exception("Cannot cast a Float to a ${type.typeName}")

            }

        }

        override fun not(a: BlimpObject): BlimpObject {
            throw Exception("Cannot not a Float")
        }

        override fun notEqual(a: BlimpObject, b: BlimpObject): BlimpObject {
            if (b.type is BooleanType) {
                return BlimpObject(Types.bool, (a.value as Boolean) != (b.value as Boolean))
            } else throw Exception("Cannot equate a Boolean with ${b.type.typeName}")
        }

        override fun equal(a: BlimpObject, b: BlimpObject): BlimpObject {
            return when (b.type) {

                is FloatType -> BlimpObject(Types.bool, (a.value as Float) == (b.value as Float))
                is IntType -> BlimpObject(Types.bool, (a.value as Float) == (b.value as Int).toFloat())

                else -> throw Exception("Cannot equate a Float with ${b.type.typeName}")

            }
        }

        override fun greater(a: BlimpObject, b: BlimpObject): BlimpObject {
            return when (b.type) {

                is FloatType -> BlimpObject(Types.bool, (a.value as Float) > (b.value as Float))
                is IntType -> BlimpObject(Types.bool, (a.value as Float) > (b.value as Int))

                else -> throw Exception("Cannot greater than on a Float with ${b.type.typeName}")

            }
        }

        override fun lesser(a: BlimpObject, b: BlimpObject): BlimpObject {
            return when (b.type) {

                is FloatType -> BlimpObject(Types.bool, (a.value as Float) < (b.value as Float))
                is IntType -> BlimpObject(Types.bool, (a.value as Float) < (b.value as Int))

                else -> throw Exception("Cannot lesser than on a Float with ${b.type.typeName}")

            }
        }

        override fun greaterEqual(a: BlimpObject, b: BlimpObject): BlimpObject {
            return when (b.type) {

                is FloatType -> BlimpObject(Types.bool, (a.value as Float) >= (b.value as Float))
                is IntType -> BlimpObject(Types.bool, (a.value as Float) >= (b.value as Int))

                else -> throw Exception("Cannot greater than or equal to on a Float with ${b.type.typeName}")

            }
        }

        override fun lesserEqual(a: BlimpObject, b: BlimpObject): BlimpObject {
            return when (b.type) {

                is FloatType -> BlimpObject(Types.bool, (a.value as Float) <= (b.value as Float))
                is IntType -> BlimpObject(Types.bool, (a.value as Float) <= (b.value as Int))

                else -> throw Exception("Cannot lesser than or equal to on a Float with ${b.type.typeName}")

            }
        }

        override fun and(a: BlimpObject, b: BlimpObject): BlimpObject {
            throw Exception("Cannot and a Float with ${b.type.typeName}")
        }

        override fun or(a: BlimpObject, b: BlimpObject): BlimpObject {
            throw Exception("Cannot or a Float with ${b.type.typeName}")
        }

        override fun add(a: BlimpObject, b: BlimpObject): BlimpObject {
            return when (b.type) {
                is FloatType -> BlimpObject(Types.float, (a.value as Float) + (b.value as Float))
                is IntType -> BlimpObject(Types.float, (a.value as Float) + (b.value as Int))
                else -> throw Exception("Cannot add a Float with ${b.type.typeName}")
            }
        }

        override fun sub(a: BlimpObject, b: BlimpObject): BlimpObject {
            return when (b.type) {
                is FloatType -> BlimpObject(Types.float, (a.value as Float) - (b.value as Float))
                is IntType -> BlimpObject(Types.float, (a.value as Float) - (b.value as Int))
                else -> throw Exception("Cannot subtract a Float with ${b.type.typeName}")
            }
        }

        override fun mul(a: BlimpObject, b: BlimpObject): BlimpObject {
            return when (b.type) {
                is FloatType -> BlimpObject(Types.float, (a.value as Float) * (b.value as Float))
                is IntType -> BlimpObject(Types.float, (a.value as Float) * (b.value as Int))
                else -> throw Exception("Cannot multiply a Float with ${b.type.typeName}")
            }
        }

        override fun div(a: BlimpObject, b: BlimpObject): BlimpObject {
            return when (b.type) {
                is FloatType -> BlimpObject(Types.float, (a.value as Float) / (b.value as Float))
                is IntType -> BlimpObject(Types.float, (a.value as Float) / (b.value as Int))
                else -> throw Exception("Cannot divide a Float with ${b.type.typeName}")
            }
        }

        override fun mod(a: BlimpObject, b: BlimpObject): BlimpObject {
            return when (b.type) {
                is FloatType -> BlimpObject(Types.float, (a.value as Float) % (b.value as Float))
                is IntType -> BlimpObject(Types.float, (a.value as Float) % (b.value as Int))
                else -> throw Exception("Cannot modulo a Float with ${b.type.typeName}")
            }
        }

    }

}