package com.dennie170.common

private class CoordinatesNotFoundException(str: String) : IllegalStateException(str)

fun <T> Array<Array<T>>.findCoordinates(find: T): Coordinates {
    for (row in indices) {
        for (col in indices) {
            if (this[row][col] == find) {
                return Coordinates(row, col)
            }
        }
    }

    throw CoordinatesNotFoundException("Can't find starting position")
}

data class Coordinates(val row: Int, val col: Int)
