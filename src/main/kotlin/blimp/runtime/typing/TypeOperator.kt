package blimp.runtime.typing

import blimp.runtime.BlimpObject

abstract class TypeOperator {

    abstract fun add(a: BlimpObject, b: BlimpObject): BlimpObject
    abstract fun sub(a: BlimpObject, b: BlimpObject): BlimpObject
    abstract fun mul(a: BlimpObject, b: BlimpObject): BlimpObject
    abstract fun div(a: BlimpObject, b: BlimpObject): BlimpObject
    abstract fun mod(a: BlimpObject, b: BlimpObject): BlimpObject

    abstract fun and(a: BlimpObject, b: BlimpObject): BlimpObject
    abstract fun or(a: BlimpObject, b: BlimpObject): BlimpObject

    abstract fun greater(a: BlimpObject, b: BlimpObject): BlimpObject
    abstract fun lesser(a: BlimpObject, b: BlimpObject): BlimpObject
    abstract fun greaterEqual(a: BlimpObject, b: BlimpObject): BlimpObject
    abstract fun lesserEqual(a: BlimpObject, b: BlimpObject): BlimpObject
    abstract fun equal(a: BlimpObject, b: BlimpObject): BlimpObject
    abstract fun notEqual(a: BlimpObject, b: BlimpObject): BlimpObject

    abstract fun not(a: BlimpObject): BlimpObject

    abstract fun cast(a: BlimpObject, type: Type): BlimpObject

}