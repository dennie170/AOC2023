package com.dennie170.challenges.year2023

import com.dennie170.Day
import com.dennie170.common.getMatrix


class Day10 : Day<Int>(2023, 10) {

    private lateinit var input: String
    private val matrix by lazy { getMatrix(input.toCharArray()) }


    override fun setUp() {
        input = super.readInput().replace("\n", "")
    }

    override fun part1(): Int {

        var startingPosition = Coordinate.ZERO
        var steps = 0
        var previousPipe: Pipe? = null


        isConnectedLoop(Coordinate(1, 1))

        return -1
    }

    data class ConnectedResult(val connected: Boolean, val steps: Int)

    private fun isConnectedLoop(startingPoint: Coordinate): ConnectedResult {
        var previousPipe: Pipe? = null
        var currentLocation = startingPoint

        var currentDirection = Direction.RIGHT
        var steps = 0

        while (true) {
            steps++
            if (previousPipe == null) {
                previousPipe = Pipe.fromChar(matrix[currentLocation.row][currentLocation.col])
                continue
            }

            if(currentLocation == startingPoint) currentLocation = currentLocation.nextColumn()

            val currentPipe = Pipe.fromChar(matrix[currentLocation.row][currentLocation.col])

            if (previousPipe.connectsWith(currentPipe)) {
                val next = getNextCoordinate(currentLocation, currentDirection, currentPipe)
                currentLocation = next.first
                currentDirection = next.second

                // calculate new direction

                previousPipe = currentPipe
                if (currentLocation == startingPoint) return ConnectedResult(true, steps)
            } else {
                return ConnectedResult(false, 0)
            }

        }
    }

    // TODO: decide based on current pipe?
    private fun getNextCoordinate(coordinate: Coordinate, direction: Direction, pipe: Pipe): Pair<Coordinate, Direction> {
        val newLocation = when (pipe) {
            Pipe.VERTICAL -> if(direction == Direction.DOWN) coordinate.nextRow() else coordinate.previousRow()
            Pipe.HORIZONTAL -> if(direction == Direction.RIGHT) coordinate.nextColumn() else coordinate.previousColumn()
            Pipe.NORTH_EAST -> if(direction.isVertical()) coordinate.nextColumn() else coordinate.previousRow()
            Pipe.NORTH_WEST -> if(direction.isVertical())  coordinate.previousColumn() else coordinate.previousRow()
            Pipe.SOUTH_WEST -> if(direction.isHorizontal()) coordinate.nextRow() else coordinate.previousColumn()
            Pipe.SOUTH_EAST -> if(direction.isHorizontal()) coordinate.nextRow() else coordinate.nextColumn()
        }

        val newDirection = when(direction) {
            Direction.RIGHT -> when(pipe) {
                Pipe.HORIZONTAL -> Direction.RIGHT
                Pipe.NORTH_WEST -> Direction.UP
                Pipe.SOUTH_WEST -> Direction.DOWN
                Pipe.VERTICAL -> throw IllegalStateException("Impossible")
                Pipe.NORTH_EAST -> throw IllegalStateException("Impossible")
                Pipe.SOUTH_EAST -> throw IllegalStateException("Impossible")
            }
            Direction.DOWN -> when(pipe) {
                Pipe.VERTICAL -> Direction.DOWN
                Pipe.NORTH_EAST ->Direction.RIGHT
                Pipe.NORTH_WEST -> Direction.LEFT
                Pipe.HORIZONTAL -> throw IllegalStateException("Impossible")
                Pipe.SOUTH_WEST -> throw IllegalStateException("Impossible")
                Pipe.SOUTH_EAST -> throw IllegalStateException("Impossible")
            }
            Direction.LEFT -> when(pipe) {
                Pipe.HORIZONTAL -> Direction.LEFT
                Pipe.NORTH_EAST -> Direction.UP
                Pipe.SOUTH_EAST -> Direction.DOWN
                Pipe.VERTICAL -> throw IllegalStateException("Impossible")
                Pipe.NORTH_WEST -> throw IllegalStateException("Impossible")
                Pipe.SOUTH_WEST -> throw IllegalStateException("Impossible")
            }
            Direction.UP ->  when(pipe) {
                Pipe.VERTICAL -> Direction.UP
                Pipe.HORIZONTAL -> throw IllegalStateException("Impossible")
                Pipe.NORTH_EAST -> throw IllegalStateException("Impossible")
                Pipe.NORTH_WEST -> throw IllegalStateException("Impossible")
                Pipe.SOUTH_WEST -> Direction.LEFT
                Pipe.SOUTH_EAST -> Direction.RIGHT
            }
        }

        return Pair(newLocation, newDirection)
    }


    override fun part2(): Int {
        return -1
    }

    enum class Direction {
        RIGHT,
        DOWN,
        LEFT,
        UP;

        fun isHorizontal(): Boolean {
            return this == RIGHT || this == LEFT
        }

        fun isVertical(): Boolean {
            return this == UP || this == DOWN
        }
    }

    enum class Pipe(val char: Char) {
        VERTICAL('|'),
        HORIZONTAL('-'),
        NORTH_EAST('L'),
        NORTH_WEST('J'),
        SOUTH_WEST('7'),
        SOUTH_EAST('F');

        fun connectsWith(pipe: Pipe): Boolean {
            return when (this) {
                VERTICAL -> when (pipe) {
                    NORTH_EAST, NORTH_WEST, VERTICAL -> true
                    else -> false
                }

                HORIZONTAL -> when (pipe) {
                    HORIZONTAL, NORTH_EAST, NORTH_WEST, SOUTH_WEST, SOUTH_EAST -> true
                    else -> false
                }

                NORTH_EAST, NORTH_WEST, SOUTH_WEST, SOUTH_EAST -> true
            }
        }

        companion object {
            fun fromChar(char: Char): Pipe {
                return when (char) {
                    '|' -> Pipe.VERTICAL
                    '-' -> Pipe.HORIZONTAL
                    'L' -> Pipe.NORTH_EAST
                    'J' -> Pipe.NORTH_WEST
                    '7' -> Pipe.SOUTH_WEST
                    'F' -> Pipe.SOUTH_EAST
                    else -> throw IllegalStateException("Pipe not legal")
                }
            }
        }
    }

    data class Coordinate(val row: Int, val col: Int) {
        init {
            if (row < 0) throw IllegalStateException("Row cannot be negative")
            if (col < 0) throw IllegalStateException("Col cannot be negative")
        }

        companion object {
            val ZERO = Coordinate(0, 0)
        }

        fun nextRow() = Coordinate(row + 1, col)
        fun nextColumn() = Coordinate(row, col + 1)

        fun previousRow() = Coordinate(row - 1, col)
        fun previousColumn() = Coordinate(row, col - 1)
    }
}
