package com.dennie170.challenges.year2023

import com.dennie170.Day
import com.dennie170.common.getMatrix
import kotlin.math.sqrt


class Day10 : Day<Int>(2023, 10) {

    private lateinit var input: String
    private lateinit var matrix: Array<Array<Char>>


    override fun setUp() {
        input = super.readInput().replace("\n", "")
        matrix = getMatrix(input.toCharArray())
    }

    override fun part1(): Int {
        return getConnectedLoop().steps / 2
    }

    private fun getConnectedLoop(): ConnectedResult {
        var maxSteps = 0

        for (row in matrix.indices) {
            for (col in matrix.indices) {
                val currentColumn = matrix[row][col]

                if (currentColumn == 'S') {
                    val result = isConnectedLoop(Coordinate(row, col), Direction.UP)

                    if (result.steps > maxSteps) {
                        maxSteps = result.steps
                    }

                    if (result.connected) {
                        return ConnectedResult(true, result.steps, result.history)
                    }

                }
            }
        }

        return ConnectedResult(false, maxSteps)
    }

    enum class Status(val status: Int) {
        OUTSIDE(0),
        WALL(1),
        INSIDE(2),
        ENCLOSED(3)
    }

    override fun part2(): Int {

        val loop = getHistoryAsCharArray(getConnectedLoop().history)

        val enclosureMatrix = Array(loop.size) { Array(loop.size) { Status.OUTSIDE } }

        for (x in 0..5) {
            // Fill enclosure matrix
            for (row in loop.indices) {
                if (row == 0 || row == loop.size - 1) continue

                var isOutside = true
                for (col in loop[row].indices) {
                    if (col == 0 || col == loop[row].size - 1) continue
                    val char = loop[row][col]

                    if (char == '0' && isOutside) continue

                    isOutside = false

                    if (char == '0') {
                        if (
                            enclosureMatrix[row - 1][col] == Status.WALL ||
                            enclosureMatrix[row + 1][col] == Status.WALL ||
                            enclosureMatrix[row][col - 1] == Status.WALL ||
                            enclosureMatrix[row][col + 1] == Status.WALL
                        ) {
                            enclosureMatrix[row][col] = Status.INSIDE
                        }
                    } else {
                        enclosureMatrix[row][col] = Status.WALL
                    }

                }
            }
        }

//        enclosureMatrix.draw()


        enclosureMatrix.reverse()

        for (x in 0..3) {
            for (row in enclosureMatrix.indices) {
                if (row == 0 || row == loop.size - 1) continue

                for (col in enclosureMatrix[row].indices) {
                    if (col == 0 || col == loop[row].size - 1) continue

                    val char = enclosureMatrix[row][col]

                    // Don't care if not inside
                    if (char != Status.INSIDE) continue

                    if (enclosureMatrix[row - 1][col] == Status.WALL || enclosureMatrix[row][col - 1] == Status.WALL || enclosureMatrix[row][col + 1] == Status.WALL) {

                        // Now check if none of the surrounding items are outside

                        if (
                            enclosureMatrix[row - 1][col] == Status.OUTSIDE ||
                            enclosureMatrix[row + 1][col] == Status.OUTSIDE ||
                            enclosureMatrix[row][col - 1] == Status.OUTSIDE ||
                            enclosureMatrix[row][col + 1] == Status.OUTSIDE
                        ) {
                            continue
                        }



                        enclosureMatrix[row][col] = Status.ENCLOSED
                    }
                }
            }
        }

        println("How about now???")

//        enclosureMatrix.draw()

        return enclosureMatrix.sumOf { it.count { s -> s == Status.ENCLOSED } }
    }

    // 727 -> too high!
    // 540 -> too High!
    // 473 -> incorrect
    // 398 -> incorrect
    // 348 -> incorrect
    // 338 -> incorrect


    data class ConnectedResult(
        val connected: Boolean,
        val steps: Int,
        val history: List<Pair<Coordinate, Char>> = listOf()
    )

    private fun isConnectedLoop(startingPoint: Coordinate, startingDirection: Direction): ConnectedResult {
        var previousPipe: Pipe? = null
        var currentLocation = startingPoint

        var currentDirection = startingDirection
        var steps = 0

        val history = mutableListOf<Pair<Coordinate, Char>>()

        while (true) {

            if (matrix[currentLocation.row][currentLocation.col] == '.') return ConnectedResult(false, steps)


            if (previousPipe == null) {
                previousPipe = Pipe.fromChar(matrix[currentLocation.row][currentLocation.col])
                continue
            }

            steps++


            if (currentLocation == startingPoint) {
                currentLocation = when (startingDirection) {
                    Direction.RIGHT -> currentLocation.nextColumn()
                    Direction.DOWN -> currentLocation.nextRow()
                    Direction.LEFT -> currentLocation.previousColumn()
                    Direction.UP -> currentLocation.previousRow()
                }
                continue
            }

            val currentPipe = Pipe.fromChar(matrix[currentLocation.row][currentLocation.col])



            if (previousPipe.connectsWith(currentPipe)) {
                history.add(
                    Pair(
                        Coordinate(currentLocation.row, currentLocation.col),
                        matrix[currentLocation.row][currentLocation.col]
                    )
                )
                val next = try {
                    getNextCoordinate(currentLocation, currentDirection, currentPipe)
                } catch (e: IllegalStateException) {
                    return ConnectedResult(false, steps)
                }
                currentLocation = next.first
                currentDirection = next.second

                // calculate new direction

                previousPipe = currentPipe
                if (currentLocation == startingPoint) return ConnectedResult(true, steps, history)
            } else {
                return ConnectedResult(false, steps)
            }

        }
    }

    // TODO: decide based on current pipe?
    private fun getNextCoordinate(
        coordinate: Coordinate,
        direction: Direction,
        pipe: Pipe
    ): Pair<Coordinate, Direction> {
        val newLocation = when (pipe) {
            Pipe.VERTICAL -> if (direction == Direction.DOWN) coordinate.nextRow() else coordinate.previousRow()
            Pipe.HORIZONTAL -> if (direction == Direction.RIGHT) coordinate.nextColumn() else coordinate.previousColumn()
            Pipe.NORTH_EAST -> if (direction.isVertical()) coordinate.nextColumn() else coordinate.previousRow()
            Pipe.NORTH_WEST -> if (direction.isVertical()) coordinate.previousColumn() else coordinate.previousRow()
            Pipe.SOUTH_WEST -> if (direction.isHorizontal()) coordinate.nextRow() else coordinate.previousColumn()
            Pipe.SOUTH_EAST -> if (direction.isHorizontal()) coordinate.nextRow() else coordinate.nextColumn()
            Pipe.START -> throw IllegalStateException("Impossible")
        }


        val newDirection = when (direction) {
            Direction.RIGHT -> when (pipe) {
                Pipe.HORIZONTAL -> Direction.RIGHT
                Pipe.NORTH_WEST -> Direction.UP
                Pipe.SOUTH_WEST -> Direction.DOWN
                Pipe.VERTICAL -> throw IllegalStateException("Impossible")
                Pipe.NORTH_EAST -> throw IllegalStateException("Impossible")
                Pipe.SOUTH_EAST -> throw IllegalStateException("Impossible")
                Pipe.START -> throw IllegalStateException("Impossible")
            }

            Direction.DOWN -> when (pipe) {
                Pipe.VERTICAL -> Direction.DOWN
                Pipe.NORTH_EAST -> Direction.RIGHT
                Pipe.NORTH_WEST -> Direction.LEFT
                Pipe.HORIZONTAL -> throw IllegalStateException("Impossible")
                Pipe.SOUTH_WEST -> throw IllegalStateException("Impossible")
                Pipe.SOUTH_EAST -> throw IllegalStateException("Impossible")
                Pipe.START -> throw IllegalStateException("Impossible")
            }

            Direction.LEFT -> when (pipe) {
                Pipe.HORIZONTAL -> Direction.LEFT
                Pipe.NORTH_EAST -> Direction.UP
                Pipe.SOUTH_EAST -> Direction.DOWN
                Pipe.VERTICAL -> throw IllegalStateException("Impossible")
                Pipe.NORTH_WEST -> throw IllegalStateException("Impossible")
                Pipe.SOUTH_WEST -> throw IllegalStateException("Impossible")
                Pipe.START -> throw IllegalStateException("Impossible")
            }

            Direction.UP -> when (pipe) {
                Pipe.VERTICAL -> Direction.UP
                Pipe.HORIZONTAL -> throw IllegalStateException("Impossible")
                Pipe.NORTH_EAST -> throw IllegalStateException("Impossible")
                Pipe.NORTH_WEST -> throw IllegalStateException("Impossible")
                Pipe.START -> throw IllegalStateException("Impossible")
                Pipe.SOUTH_WEST -> Direction.LEFT
                Pipe.SOUTH_EAST -> Direction.RIGHT
            }
        }

        return Pair(newLocation, newDirection)
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
        SOUTH_EAST('F'),
        START('S');

        fun connectsWith(pipe: Pipe): Boolean {
            return when (this) {
                VERTICAL -> when (pipe) {
                    NORTH_EAST, NORTH_WEST, VERTICAL, SOUTH_WEST, SOUTH_EAST -> true
                    else -> false
                }

                HORIZONTAL -> when (pipe) {
                    HORIZONTAL, NORTH_EAST, NORTH_WEST, SOUTH_WEST, SOUTH_EAST -> true
                    else -> false
                }

                NORTH_EAST, NORTH_WEST, SOUTH_WEST, SOUTH_EAST -> true
                START -> true
            }
        }

        companion object {
            fun fromChar(char: Char): Pipe {
                return when (char) {
                    '|' -> VERTICAL
                    '-' -> HORIZONTAL
                    'L' -> NORTH_EAST
                    'J' -> NORTH_WEST
                    '7' -> SOUTH_WEST
                    'F' -> SOUTH_EAST
                    'S' -> START
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

    private fun getHistoryAsCharArray(history: List<Pair<Coordinate, Char>>): Array<Array<Char>> {
        val ret = Array(sqrt(input.length.toDouble()).toInt()) { Array(sqrt(input.length.toDouble()).toInt()) { '0' } }

        for (hist in history) {
            val (coord, char) = hist

            ret[coord.row][coord.col] = char
        }

        return ret
    }

    private fun drawResult(history: List<Pair<Coordinate, Char>>, transform: Boolean = true) {
        val m = getHistoryAsCharArray(history)
        for (row in m) {
            println()
            for (col in row) {
                val char = if (transform) when (col) {
                    '-' -> '\u2501'
                    '|' -> '\u2503'
                    'F' -> '\u250F'
                    'J' -> '\u251B'
                    'L' -> '\u2517'
                    '7' -> '\u2513'
                    else -> col
                } else col

                print(char)
            }
        }
    }

    private fun Array<Array<Status>>.draw() {
        for (row in indices) {
            println()
            for (col in this[row].indices) {
                print(this[row][col].status)
            }
        }
    }
}

