package com.dennie170.challenges.year2024

import com.dennie170.Day
import com.dennie170.common.getMatrix

class Day6 : Day<Int>(2024, 6) {

    private lateinit var input: String
    private lateinit var matrix: Array<Array<Char>>

    override fun setUp() {
        input = super.readInput().replace("\n", "")
        matrix = getMatrix(input.toCharArray())
    }

    data class Coordinates(val row: Int, val col: Int)
    data class Location(val direction: Direction, val coordinates: Coordinates)

    enum class Direction {
        RIGHT,
        DOWN,
        LEFT,
        UP,
    }

    private var currentDirection = Direction.UP
    private lateinit var currentPosition: Coordinates
    private val positions = mutableSetOf<Coordinates>()

    private lateinit var extraObstruction: Coordinates

    private var outOfBounds = false

    var part = 1

    override fun part1(): Int {
        extraObstruction = Coordinates(-1, -1)

        findStartingPosition()

        while (!outOfBounds) {

            when (currentDirection) {
                Direction.UP -> walkUp()
                Direction.DOWN -> walkDown()
                Direction.LEFT -> walkLeft()
                Direction.RIGHT -> walkRight()
            }

        }

        return positions.size + 1
    }

    private fun findStartingPosition() {
        for (row in matrix.indices) {
            for (col in matrix.indices) {
                if (matrix[row][col] == '^') {
                    currentPosition = Coordinates(row, col)
                    return
                }
            }
        }

        throw IllegalStateException("Can't find starting position")
    }

    private fun walkUp() {
        for (row in currentPosition.row - 1 downTo 0) {
            if (part == 2) {
                currentRoute.add(Location(currentDirection, currentPosition))
            }

            if (matrix[row][currentPosition.col] == '#' || Coordinates(row, currentPosition.col) == extraObstruction) {
                break
            }

            if (row - 1 < 0) {
                outOfBounds = true
                return
            }

            currentPosition = currentPosition.copy(row = row)
            positions.add(currentPosition)

            if (part == 2 && currentRoute.contains(Location(currentDirection, currentPosition))) {
                isInfiniteRoute = true
                return
            }
        }

        currentDirection = Direction.RIGHT
    }

    private fun walkDown() {
        for (row in currentPosition.row + 1..<matrix.size) {
            if (part == 2) {
                currentRoute.add(Location(currentDirection, currentPosition))
            }

            if (matrix[row][currentPosition.col] == '#' || Coordinates(row, currentPosition.col) == extraObstruction) {
                break
            }

            if (row + 1 >= matrix.size) {
                outOfBounds = true
                return
            }

            currentPosition = currentPosition.copy(row = row)
            positions.add(currentPosition)

            if (part == 2 && currentRoute.contains(Location(currentDirection, currentPosition))) {
                isInfiniteRoute = true
                return
            }
        }

        currentDirection = Direction.LEFT
    }

    private fun walkLeft() {
        for (col in currentPosition.col - 1 downTo 0) {

            if (part == 2) {
                currentRoute.add(Location(currentDirection, currentPosition))
            }

            if (matrix[currentPosition.row][col] == '#' || Coordinates(currentPosition.row, col) == extraObstruction) {
                break
            }

            if (col - 1 < 0) {
                outOfBounds = true
                return
            }


            currentPosition = currentPosition.copy(col = col)
            positions.add(currentPosition)


            if (part == 2 && currentRoute.contains(Location(currentDirection, currentPosition))) {
                isInfiniteRoute = true
                return
            }
        }

        currentDirection = Direction.UP
    }

    private fun walkRight() {
        for (col in currentPosition.col + 1..<matrix.size) {
            if (part == 2) {
                currentRoute.add(Location(currentDirection, currentPosition))
            }

            if (matrix[currentPosition.row][col] == '#' || Coordinates(currentPosition.row, col) == extraObstruction) {
                break
            }

            if (col + 1 >= matrix.size) {
                outOfBounds = true
                return
            }

            currentPosition = currentPosition.copy(col = col)
            positions.add(currentPosition)

            if (part == 2 && currentRoute.contains(Location(currentDirection, currentPosition))) {
                isInfiniteRoute = true
                return
            }
        }

        currentDirection = Direction.DOWN
    }

    private var infiniteLoops = 0
    private val currentRoute = mutableSetOf<Location>()
    private var isInfiniteRoute = false

    override fun part2(): Int {
        part = 2
        // Reset
        currentDirection = Direction.UP

        positions.clear()
        outOfBounds = false

        findStartingPosition()

        val startingPosition = currentPosition

//        extraObstruction = Coordinates(6, 3)

        for (row in matrix.indices) {
            for (col in matrix.indices) {
                if (matrix[row][col] == '#' || matrix[row][col] == '^') continue

                extraObstruction = Coordinates(row, col)
                currentRoute.clear()
                outOfBounds = false
                currentDirection = Direction.UP
                currentPosition = startingPosition
                isInfiniteRoute = false

                while (true) {

                    when (currentDirection) {
                        Direction.UP -> walkUp()
                        Direction.DOWN -> walkDown()
                        Direction.LEFT -> walkLeft()
                        Direction.RIGHT -> walkRight()
                    }


                    if (outOfBounds) {
                        // Set obstruction to next location
                        break
                    }

                    if (isInfiniteRoute) {
                        infiniteLoops++
                        break
                    }
                }
            }
        }

        return infiniteLoops
    }
}
