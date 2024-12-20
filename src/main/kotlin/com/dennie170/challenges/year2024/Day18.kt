package com.dennie170.challenges.year2024

import com.dennie170.Day
import com.dennie170.common.Coordinates
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class Day18 : Day<String>(2024, 18) {

    private val lines: List<String> = readInput().lines()

    private companion object {
        const val GRID_DIMENSION = 70
    }

    private fun parseByte(line: String): Coordinates {
        val (x, y) = line.split(',').map { it.toInt() }

        return Coordinates(y, x)
    }

    override fun part1(): String {
        return lines.subList(0, 1024).map(::parseByte).toSet().let { bytes ->
            (solve(getMaze(bytes)).size - 1).toString()
        }
    }

    private fun draw(bytes: Set<Coordinates>) {
        for (row in 0..GRID_DIMENSION) {
            for (col in 0..GRID_DIMENSION) {
                if (bytes.contains(Coordinates(row, col))) {
                    print('#')
                } else {
                    print('.')
                }
            }
            println()
        }
    }

    private fun List<Node>.draw(bytes: Set<Coordinates>) {
        for (row in 0..GRID_DIMENSION) {
            for (col in 0..GRID_DIMENSION) {
                if (bytes.contains(Coordinates(row, col))) {
                    print('#')
                } else if (find { it.coordinates == Coordinates(row, col) } != null) {
                    print('O')
                } else {
                    print('.')
                }
            }
            println()
        }
    }

    private fun getMaze(bytes: Set<Coordinates>): Array<IntArray> {
        val maze = Array(GRID_DIMENSION + 1) { IntArray(GRID_DIMENSION + 1) { 0 } }

        for (byte in bytes) {
            maze[byte.row][byte.col] = 1
        }

        return maze
    }


    private data class Node(val coordinates: Coordinates, val parent: Node?)

    private fun solve(maze: Array<IntArray>): Set<Node> {
        val nextToVisit = LinkedList<Node>()
        val visited = HashSet<Coordinates>()

        val start = Coordinates(0, 0)

        nextToVisit.add(Node(start, null))

        val finish = Coordinates(GRID_DIMENSION, GRID_DIMENSION)

        val directions = arrayOf(intArrayOf(0, 1), intArrayOf(1, 0), intArrayOf(0, -1), intArrayOf(-1, 0))


        while (nextToVisit.isNotEmpty()) {
            val node = nextToVisit.removeFirst()

            if (visited.contains(node.coordinates)) {
                continue
            }

            if (maze[node.coordinates.row][node.coordinates.col] == 1) { // It's a wall
                visited.add(node.coordinates)
                continue
            }

            if (node.coordinates == finish) {
                val path = mutableSetOf<Node>()
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

                if (node.coordinates.row + y !in maze.indices || node.coordinates.col + x !in maze.indices) continue

                val new = Coordinates(node.coordinates.row + y, node.coordinates.col + x)

                nextToVisit.add(Node(new, node))
                visited.add(node.coordinates)
            }
        }

        return emptySet()
    }

    private fun solveOnThread(amount: Int): String? {
        val bytes = lines.subList(0, amount).map(::parseByte).toSet()

        val path = solve(getMaze(bytes))

        if (path.isEmpty()) {
            return lines[amount - 1]
        }

        return null
    }

    override fun part2(): String {
        val executor = Executors.newVirtualThreadPerTaskExecutor()

        val result = ConcurrentHashMap<Int, String>()

        // We can skip the first 1024 runs as we know those work
        for (amount in 1024..<lines.size) {
            executor.submit {
                val res = solveOnThread(amount)

                if (!res.isNullOrEmpty()) {
                    result[amount] = res
                    executor.shutdownNow()
                }
            }
        }

        executor.awaitTermination(1, TimeUnit.SECONDS)

        if (result.isEmpty()) throw IllegalStateException("Could not find blocking byte...")

        return result.toSortedMap().firstEntry().value
    }
}
