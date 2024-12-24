package com.dennie170.challenges.year2024

import com.dennie170.Day
import com.dennie170.common.Coordinates
import java.util.*

class Day21 : Day<Int>(2024, 21) {

    val input: String = readInput()


    private companion object {
        val NUMERIC_KEYPAD = arrayOf(
            charArrayOf('7', '8', '9'),
            charArrayOf('4', '5', '6'),
            charArrayOf('1', '2', '3'),
            charArrayOf('\u0000', '0', 'A')
        )
        val NUMERIC_STARTING_POSITION = Coordinates(3, 2)
        val NUMERIC_A_POSITION = Coordinates(3, 2)


        val DIRECTIONAL_KEYPAD = arrayOf(
            charArrayOf('\u0000', '^', 'A'),
            charArrayOf('<', 'v', '>')
        )

    }

    private var currentNumericPosition = NUMERIC_STARTING_POSITION

    private fun printOperator(y: Int, x: Int) {
        val direction = intArrayOf(y, x)

        val char = when {
            direction.contentEquals(intArrayOf(-1, 0)) -> {
                '^'
            }

            direction.contentEquals(intArrayOf(1, 0)) -> {
                'v'
            }

            direction.contentEquals(intArrayOf(0, 1)) -> {
                '>'
            }

            direction.contentEquals(intArrayOf(0, -1)) -> {
                '<'
            }

            else -> throw IllegalStateException("Illegal operator")
        }

        print(char)
    }

    // Navigate from to coordinates. Returns number of steps taken
    private fun move(from: Coordinates, to: Coordinates): Int {
        var moves = 0

        val path = findShortestPath(NUMERIC_KEYPAD, from, to)

        for (step in path.windowed(2)) {
            val numericalStartPosition = step[0]
            val numericalEndPosition = step[1]

            val y = if (numericalEndPosition.row > numericalStartPosition.row) 1 else if (numericalEndPosition.row < numericalStartPosition.row) -1 else 0
            val x = if (numericalEndPosition.col > numericalStartPosition.col) 1 else if (numericalEndPosition.col < numericalStartPosition.col) -1 else 0

            printOperator(y, x)

            moves++
        }

        return moves
    }

    private fun solveCode(code: CharArray): Int {
        val shortestNumericPath = code.toList().windowed(2).map {
            val startCoordinates = NUMERIC_KEYPAD.findCoordinates(it[0])
            val endCoordinates = NUMERIC_KEYPAD.findCoordinates(it[1])

            findShortestPath(NUMERIC_KEYPAD, startCoordinates, endCoordinates)
        }

        var moves = 0

        // Move from A to first number
        moves += move(NUMERIC_STARTING_POSITION, Coordinates(3, 1))

        print('A')


        for (keystroke in shortestNumericPath) {


            for (move in keystroke.windowed(2)) {
                val numericalStartPosition = move[0]
                val numericalEndPosition = move[1]

                moves += move(numericalStartPosition, numericalEndPosition)

            }

            print('A')
        }

        return moves * code.filter { it.isDigit() }.fold("") { acc, int -> acc + int}.toInt()
    }

    override fun part1(): Int {
        var moves = 0

        moves += solveCode(charArrayOf('0', '2', '9', 'A'))


        return moves
    }


    override fun part2(): Int {
        TODO()
    }


    private data class Node(val coordinates: Coordinates, val parent: Node?)

    private fun findShortestPath(matrix: Array<CharArray>, start: Coordinates, finish: Coordinates): Set<Coordinates> {
        val nextToVisit = LinkedList<Node>()
        val visited = mutableSetOf<Coordinates>()

        nextToVisit.add(Node(start, null))

        val directions = arrayOf(intArrayOf(0, 1), intArrayOf(1, 0), intArrayOf(0, -1), intArrayOf(-1, 0))

        while (nextToVisit.isNotEmpty()) {
            val node = nextToVisit.removeFirst()

            if (visited.contains(node.coordinates)) {
                continue
            }

            if (node.coordinates.row in matrix.indices && node.coordinates.col in matrix[node.coordinates.row].indices && (matrix[node.coordinates.row][node.coordinates.col] == '\u0000')) { // Illegal position
                visited.add(node.coordinates)
                continue
            }

            if (node.coordinates == finish) {
                val path = LinkedHashSet<Node>()
                var current: Node? = node

                // Loop back over all the parents till we're back at the start
                while (current != null) {
                    path.add(current)
                    current = current.parent
                }

                return path.map { it.coordinates }.reversed().toSet()
            }

            for (direction in directions) {
                val (x, y) = direction

                if (node.coordinates.row + y !in matrix.indices || node.coordinates.col + x !in matrix.indices) continue

                val new = Coordinates(node.coordinates.row + y, node.coordinates.col + x)

                nextToVisit.add(Node(new, node))
                visited.add(node.coordinates)
            }
        }

        return setOf()
    }

    fun Array<CharArray>.findCoordinates(find: Char): Coordinates {
        for (row in indices) {
            for (col in indices) {
                if (col in this[row].indices && this[row][col] == find) {
                    return Coordinates(row, col)
                }
            }
        }

        throw IllegalStateException("Can't find position")
    }

}
