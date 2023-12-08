package com.dennie170.common

fun <T> List<Set<T>>.intersectAll(): Set<T> {
    var result = this[0]

    if (isEmpty()) return result

    for (i in 1..<size) {
        result = result.intersect(this[i])
    }

    return result
}
