package blimp.util

import java.util.*

fun <T: Any> List<T>.toStack(): Stack<T> {

    val revList = this.reversed()
    val result = Stack<T>()

    for (el in revList) result.push(el)

    return result
}