package com.dennie170.challenges.year2024

import com.dennie170.Day
import com.dennie170.common.Coordinates
import com.dennie170.common.getMatrix


class Day16 : Day<Int>(2024, 16) {

    private val input = readInput()

    private val directions = arrayOf(intArrayOf(0, 1), intArrayOf(1, 0), intArrayOf(0, -1), intArrayOf(-1, 0))

    data class Coordinate(val row: Int, val col: Int)

    private fun getNextCoordinate(row: Int, col: Int, i: Int, j: Int) = Coordinate(row + i, col + j)

    enum class Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT;

        companion object {
            fun fromInts(ints: IntArray): Direction {
                return when {
                    ints.contentEquals(intArrayOf(0, 1)) -> RIGHT
                    ints.contentEquals(intArrayOf(1, 0)) -> UP
                    ints.contentEquals(intArrayOf(0, -1)) -> LEFT
                    ints.contentEquals(intArrayOf(-1, 0)) -> DOWN
                    else -> throw IllegalStateException("Illegal direction ${ints.contentToString()}")
                }
            }
        }
    }

    // 871862 -> too high
    override fun part1(): Int {
        val matrix = getMatrix(input.toCharArray().filter { it != '\n' }.toCharArray())

        val steps = solve(matrix)

        var lastDirection: Direction? = null
        var lastPosition: Coordinates? = null

        var cost = 0

        for (step in steps) {
            if (lastPosition === null) {
                lastPosition = step
                continue
            }

            val direction = Direction.fromInts(intArrayOf(step.row - lastPosition.row, step.col - lastPosition.col))

            if(direction != lastDirection) {
                cost += 1000
                lastDirection = direction
            } else cost++


            lastPosition = step
        }

        return cost
    }

    private fun solve(matrix: Array<Array<Char>>): List<Coordinates> {
        val path = mutableListOf<Coordinates>()

        // Start exploring from the start index
        if (explore(matrix, 12, 1, path)) {
            return path
        }
        return emptyList()
    }

    private fun isOutOfBounds(matrix: Array<Array<Char>>, row: Int, col: Int): Boolean {
        return row < 0 || row >= matrix.size || col < 0 || col >= matrix.size
    }

    private val exploredNodes = mutableSetOf<Coordinates>()

    private fun explore(matrix: Array<Array<Char>>, row: Int, col: Int, path: MutableList<Coordinates>): Boolean {
        if (
            isOutOfBounds(matrix, row, col)
            || matrix[row][col] == '#'
            || exploredNodes.contains(Coordinates(row, col))
        ) {
            return false
        }

        path.add(Coordinates(row, col))
        exploredNodes.add(Coordinates(row, col))

        // Quit at the end
        if (matrix[row][col] == 'E') return true

        for (direction in directions) {
            if (explore(matrix, row + direction[0], col +  direction[1], path)) {
                return true
            }
        }

        path.removeAt(path.size - 1)
        return false

    }

    override fun part2(): Int {
        TODO()
    }


}


