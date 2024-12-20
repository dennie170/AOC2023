package com.dennie170.challenges.year2024

import com.dennie170.Day
import com.dennie170.common.Coordinates
import com.dennie170.common.findCoordinates
import com.dennie170.common.getMatrix
import java.util.*
import kotlin.math.absoluteValue

class Day20 : Day<Int>(2024, 20) {

    val input: String = readInput()

    private val searchArray = charArrayOf('.', 'S', 'E')

    override fun part1(): Int {
        val matrix = getMatrix(input.toCharArray().filter { it != '\n' }.toCharArray())

        val start = matrix.findCoordinates('S')
        val finish = matrix.findCoordinates('E')

        val originalPath = solve(matrix, start, finish).map { it.coordinates }.toTypedArray()

        val cheats = findAllPossibleCheats(matrix)

        return cheats.count { cheat ->
            when {
                matrix[cheat.row - 1][cheat.col] == '.' && matrix[cheat.row + 1][cheat.col] == '.' -> {
                    val startCheat = originalPath.indexOf(Coordinates(cheat.row - 1, cheat.col))
                    val endCheat = originalPath.indexOf(Coordinates(cheat.row + 1, cheat.col))

                    // subtract 2 to remove the starting position from both start and end list
                    (startCheat - endCheat).absoluteValue - 2 >= 100

                }
                matrix[cheat.row][cheat.col - 1] == '.' && matrix[cheat.row][cheat.col + 1] == '.' -> {
                    val startCheat = originalPath.indexOf(Coordinates(cheat.row, cheat.col - 1))
                    val endCheat = originalPath.indexOf(Coordinates(cheat.row, cheat.col + 1))

                    // subtract 2 to remove the starting position from both start and end list
                    (startCheat - endCheat).absoluteValue - 2 >= 100
                }
                else -> false
            }

        }
    }


    private fun findAllPossibleCheats(matrix: Array<Array<Char>>): List<Coordinates> {
        val queue = mutableListOf<Coordinates>()

        for (row in matrix.indices) {

            for (col in matrix.indices) {
                if (matrix[row][col] == '#') {

                    if (row - 1 >= 0 && row + 1 < matrix.size && matrix[row - 1][col] in searchArray && matrix[row + 1][col] in searchArray) {
                        queue.add(Coordinates(row, col))
                    } else if (col - 1 >= 0 && col + 1 < matrix.size && matrix[row][col - 1] in searchArray && matrix[row][col + 1] in searchArray) {
                        queue.add(Coordinates(row, col))
                    }

                }
            }
        }

        return queue
    }


    private data class Node(val coordinates: Coordinates, val parent: Node?)

    private fun solve(matrix: Array<Array<Char>>, start: Coordinates, finish: Coordinates): Set<Node> {
        val nextToVisit = LinkedList<Node>()
        val visited = mutableSetOf<Coordinates>()

        nextToVisit.add(Node(start, null))

        val directions = arrayOf(intArrayOf(0, 1), intArrayOf(1, 0), intArrayOf(0, -1), intArrayOf(-1, 0))


        while (nextToVisit.isNotEmpty()) {
            val node = nextToVisit.removeFirst()

            if (visited.contains(node.coordinates)) {
                continue
            }

            if ((matrix[node.coordinates.row][node.coordinates.col] == '#')) { // It's a wall
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

                return path
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


    override fun part2(): Int {
        TODO()
    }
}
