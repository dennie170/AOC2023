package com.dennie170.common

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
