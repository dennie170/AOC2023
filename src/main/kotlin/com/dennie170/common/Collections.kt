package com.dennie170.common

fun <T> List<Set<T>>.intersectAll(): Set<T> {
    var result = this[0]

    if (isEmpty()) return result

    for (i in 1..<size) {
        result = result.intersect(this[i])
    }

    return result
}

// Returns all indexes of items that match predicate
fun <E> Iterable<E>.indexesOf(e: (E) -> Boolean): List<Int> {
    return mapIndexedNotNull { index, elem -> index.takeIf { e(elem) } }
}

data class Tuple2<A, B>(val t1: A, val t2: B)

fun List<Char>.charListToString(): String {
    val sb = StringBuilder(size)
    for (char in this) sb.append(char)
    return sb.toString()
}
