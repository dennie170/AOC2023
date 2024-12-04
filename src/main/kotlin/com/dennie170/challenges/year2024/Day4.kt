package com.dennie170.challenges.year2024

import com.dennie170.Day
import com.dennie170.common.getMatrix
import com.dennie170.common.isEither

class Day4 : Day<Int>(2024, 4) {

    private lateinit var input: String
    private lateinit var matrix: Array<Array<Char>>

    data class Coordinates(val row: Int, val col: Int)

    enum class Direction {
        RIGHT,
        DOWN,
        LEFT,
        UP,
        DIAGONAL_LEFT_UP,
        DIAGONAL_RIGHT_UP,
        DIAGONAL_LEFT_DOWN,
        DIAGONAL_RIGHT_DOWN,
    }

    override fun setUp() {
        input = super.readInput().replace("\n", "")
        matrix = getMatrix(input.toCharArray())
    }

    private fun spellsXmas(startCoordinates: Coordinates, direction: Direction): Boolean {
        return when {
            direction == Direction.LEFT && startCoordinates.col - 3 >= 0 -> spellsXmasLeft(startCoordinates)
            direction == Direction.RIGHT && startCoordinates.col + 3 < matrix.size -> spellsXmasRight(startCoordinates)
            direction == Direction.UP && startCoordinates.row - 3 >= 0 -> spellsXmasUp(startCoordinates)
            direction == Direction.DOWN && startCoordinates.row + 3 < matrix.size -> spellsXmasDown(startCoordinates)
            direction == Direction.DIAGONAL_LEFT_UP && startCoordinates.row - 3 >= 0 && startCoordinates.col - 3 >= 0 -> spellsXmasDiagonalLeftUp(startCoordinates)
            direction == Direction.DIAGONAL_RIGHT_UP && startCoordinates.row - 3 >= 0 && startCoordinates.col + 3 < matrix.size -> spellsXmasDiagonalRightUp(startCoordinates)
            direction == Direction.DIAGONAL_LEFT_DOWN && startCoordinates.row + 3 < matrix.size && startCoordinates.col - 3 >= 0 -> spellsXmasDiagonalLeftDown(startCoordinates)
            direction == Direction.DIAGONAL_RIGHT_DOWN && startCoordinates.row + 3 < matrix.size && startCoordinates.col + 3 < matrix.size -> spellsXmasDiagonalRightDown(startCoordinates)
            else -> false
        }
    }

    private fun spellsXmasLeft(startCoordinates: Coordinates): Boolean {
        return matrix[startCoordinates.row][startCoordinates.col - 1] == 'M'
                && matrix[startCoordinates.row][startCoordinates.col - 2] == 'A'
                && matrix[startCoordinates.row][startCoordinates.col - 3] == 'S'
    }

    private fun spellsXmasRight(startCoordinates: Coordinates): Boolean {
        return matrix[startCoordinates.row][startCoordinates.col + 1] == 'M'
                && matrix[startCoordinates.row][startCoordinates.col + 2] == 'A'
                && matrix[startCoordinates.row][startCoordinates.col + 3] == 'S'
    }

    private fun spellsXmasUp(startCoordinates: Coordinates): Boolean {
        return matrix[startCoordinates.row - 1][startCoordinates.col] == 'M'
                && matrix[startCoordinates.row - 2][startCoordinates.col] == 'A'
                && matrix[startCoordinates.row - 3][startCoordinates.col] == 'S'
    }


    private fun spellsXmasDown(startCoordinates: Coordinates): Boolean {
        return matrix[startCoordinates.row + 1][startCoordinates.col] == 'M'
                && matrix[startCoordinates.row + 2][startCoordinates.col] == 'A'
                && matrix[startCoordinates.row + 3][startCoordinates.col] == 'S'
    }

    private fun spellsXmasDiagonalLeftUp(startCoordinates: Coordinates): Boolean {
        return matrix[startCoordinates.row - 1][startCoordinates.col - 1] == 'M'
                && matrix[startCoordinates.row - 2][startCoordinates.col - 2] == 'A'
                && matrix[startCoordinates.row - 3][startCoordinates.col - 3] == 'S'
    }

    private fun spellsXmasDiagonalRightUp(startCoordinates: Coordinates): Boolean {
        return matrix[startCoordinates.row - 1][startCoordinates.col + 1] == 'M'
                && matrix[startCoordinates.row - 2][startCoordinates.col + 2] == 'A'
                && matrix[startCoordinates.row - 3][startCoordinates.col + 3] == 'S'
    }


    private fun spellsXmasDiagonalLeftDown(startCoordinates: Coordinates): Boolean {
        return matrix[startCoordinates.row + 1][startCoordinates.col - 1] == 'M'
                && matrix[startCoordinates.row + 2][startCoordinates.col - 2] == 'A'
                && matrix[startCoordinates.row + 3][startCoordinates.col - 3] == 'S'
    }

    private fun spellsXmasDiagonalRightDown(startCoordinates: Coordinates): Boolean {
        return matrix[startCoordinates.row + 1][startCoordinates.col + 1] == 'M'
                && matrix[startCoordinates.row + 2][startCoordinates.col + 2] == 'A'
                && matrix[startCoordinates.row + 3][startCoordinates.col + 3] == 'S'
    }

    override fun part1(): Int {
        val xCoordinates = mutableSetOf<Coordinates>()

        for ((rowIndex, row) in matrix.withIndex()) {
            for ((colIndex, col) in row.withIndex()) {
                if (col == 'X') xCoordinates.add(Coordinates(rowIndex, colIndex))
            }
        }

        var matches = 0
        for (coordinates in xCoordinates) {
            for (direction in Direction.entries) {
                if (spellsXmas(coordinates, direction)) {
                    matches++
                }
            }
        }

        return matches
    }

    // 2859 -> too high
    override fun part2(): Int {
        val aCoordinates = mutableSetOf<Coordinates>()

        for ((rowIndex, row) in matrix.withIndex()) {
            for ((colIndex, col) in row.withIndex()) {
                if (col == 'A') aCoordinates.add(Coordinates(rowIndex, colIndex))
            }
        }

        return aCoordinates.count { coordinates ->
            try {
                ((matrix[coordinates.row - 1][coordinates.col - 1].isEither('M', 'S'))
                        && (matrix[coordinates.row - 1][coordinates.col + 1].isEither('M', 'S'))
                        && (matrix[coordinates.row + 1][coordinates.col - 1].isEither('M', 'S'))
                        && (matrix[coordinates.row + 1][coordinates.col + 1].isEither('M', 'S'))
                        && (matrix[coordinates.row + 1][coordinates.col - 1] != matrix[coordinates.row - 1][coordinates.col + 1]) // left bottom may not match right top
                        && (matrix[coordinates.row - 1][coordinates.col - 1] != matrix[coordinates.row + 1][coordinates.col + 1])) // left bottom may not match right top
                // left top may not match right bottom
            } catch(_: ArrayIndexOutOfBoundsException) {
                false
            }
        }
    }
}
