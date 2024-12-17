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

        fun toIntArray(): IntArray {
            return when (this) {
                RIGHT -> intArrayOf(0, 1)
                UP -> intArrayOf(1, 0)
                LEFT -> intArrayOf(0, -1)
                DOWN -> intArrayOf(-1, 0)
            }
        }

        companion object {
            fun fromIntArray(ints: IntArray): Direction {
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
    // 872734 -> too high
    override fun part1(): Int {
        val matrix = getMatrix(input.toCharArray().filter { it != '\n' }.toCharArray())

        var lastDirection: Direction? = null
        var lastPosition: Coordinates? = null


        val paths = solve(matrix, 139, 1)


        return possibleRoutes.minOf { getCost(it) }
    }

    private fun getCost(walkedPath: Set<Coordinates>): Int {

        var lastDirection = Direction.RIGHT
        var lastPosition: Coordinates? = null

        var cost = 0

        for (position in walkedPath) {
            if (lastPosition == null) {
                cost++
                lastPosition = position
                continue
            }

            val direction = Direction.fromIntArray(intArrayOf(lastPosition.row - position.row, lastPosition.col - position.col))

            if (direction !== lastDirection) {
                cost += 1000
                lastDirection = direction
            }


            cost++
            lastPosition = position
        }

        return cost
    }

    private data class Route(val path: List<Coordinates>, val cost: Int)

    private val possibleRoutes = mutableListOf<Set<Coordinates>>()

    private val exploredNodes = mutableSetOf<Coordinates>()


    private fun solve(matrix: Array<Array<Char>>, row: Int, col: Int, walkedPath: Set<Coordinates> = linkedSetOf()): Boolean {

        if (matrix[row][col] == 'E') {
            possibleRoutes.add(walkedPath.plus(Coordinates(row, col)))

            return true
        }

//        exploredNodes.add(Coordinates(row, col))

        for (direction in directions) {
            val (y, x) = direction
            if (!isOutOfBounds(matrix, row + y, col + x)
                && !walkedPath.contains(Coordinates(row + y, col + x))
//                && !exploredNodes.contains(Coordinates(row + y, col + x))
                && matrix[row + y][col + x] != '#'
            ) {
                solve(matrix, row + y, col + x, walkedPath.plus(Coordinates(row + y, col + x)))
            }
        }

        return false
    }


    private fun walk() {

    }

    private fun isOutOfBounds(matrix: Array<Array<Char>>, row: Int, col: Int): Boolean {
        return row < 0 || row >= matrix.size || col < 0 || col >= matrix.size
    }


    override fun part2(): Int {
        TODO()
    }


}


