package com.dennie170.common

import kotlin.math.absoluteValue

fun findLeastCommonMultiple(a: Long, b: Long): Long {
    if (a == 0L || b == 0L) return 0L

    val absA = a.absoluteValue
    val absB = b.absoluteValue

    return absA / absA.gcd(absB) * absB
}

fun findLeastCommonMultiple(numbers: List<Long>): Long {
    var result = numbers[0]
    for (i in 1..<numbers.size) {
        result = findLeastCommonMultiple(result, numbers[i])
    }
    return result
}

private tailrec fun Long.gcd(b: Long): Long = if (b == 0L) this else b.gcd(this % b)

/**
 * Checks if 2 IntRanges are completely overlapping one another
 * E.g.
 *  (1-10 & 4-6) = true
 *  (1-10 & 7-15) = false
 */
fun IntRange.Companion.areCompletelyOverlapping(a: IntRange, b: IntRange): Boolean {
    return (a.first <= b.first && a.last >= b.last) || (b.first <= a.first && b.last >= a.last)
}
