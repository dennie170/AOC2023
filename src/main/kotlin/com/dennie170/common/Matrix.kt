package com.dennie170.common

import com.dennie170.challenges.year2023.Day10
import kotlin.math.sqrt

fun getMatrix(input: CharArray): Array<Array<Char>> {
    // Calculate total line length and lines to draw matrix
    val sqrt = sqrt(input.size.toDouble()).toInt()

    val matrix: Array<Array<Char>> = Array(sqrt) { Array(sqrt) { '0' } }

    for (line in 0..<sqrt) {
        for (char in 0..<sqrt) {

            // Fill the matrix with the correct chars
            matrix[line][char] = input[char + (line * sqrt)]
        }
    }

    return matrix
}

fun getMatrix(input: List<Int>): Array<IntArray> {
    val sqrt = sqrt(input.size.toDouble()).toInt()

    val matrix: Array<IntArray> = Array(sqrt) { IntArray(sqrt) }

    for (line in 0..<sqrt) {
        for (col in 0..<sqrt) {
            matrix[line][col] = input[col + (line * sqrt)]
        }
    }

    return matrix
}

fun Array<Array<Int>>.draw() {
    for (row in indices) {
        println()
        for (col in this[row].indices) {
            print(this[row][col])
        }
    }
}
