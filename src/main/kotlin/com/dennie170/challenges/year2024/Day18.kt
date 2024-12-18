package com.dennie170.challenges.year2024

import com.dennie170.Day
import com.dennie170.common.Coordinates
import java.util.*


class Day18 : Day<Int>(2024, 18) {

    private val lines: List<String> = readInput().lines()

    private val bytes = mutableSetOf<Coordinates>()

    companion object {
        const val GRID_DIMENSION = 70
    }

    override fun part1(): Int {

        for (line in lines.subList(0, 1024)) {
            val (x, y) = line.split(',').map { it.toInt() }

            bytes.add(Coordinates(y, x))
        }

        val shortedPath = solve(getMaze())

        return shortedPath.size - 1
    }

    private fun draw() {
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

    private fun List<Node>.draw() {
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

    private fun getMaze(): Array<IntArray> {
        val maze = Array(GRID_DIMENSION + 1) { IntArray(GRID_DIMENSION + 1) { 0 } }

        for(byte in bytes) {
            maze[byte.row][byte.col] = 1
        }

        return maze
    }

    private val directions = arrayOf(intArrayOf(0, 1), intArrayOf(1, 0), intArrayOf(0, -1), intArrayOf(-1, 0))

    private data class Node(val coordinates: Coordinates, val parent: Node?)

    private fun solve(maze: Array<IntArray>): Set<Node> {
        val nextToVisit = LinkedList<Node>()
        val visited = HashSet<Coordinates>()

        val start = Coordinates(0, 0)

        nextToVisit.add(Node(start, null))

        val finish = Coordinates(GRID_DIMENSION, GRID_DIMENSION)

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

                if(node.coordinates.row + y !in maze.indices || node.coordinates.col + x !in maze.indices) continue

                val new = Coordinates(node.coordinates.row + y, node.coordinates.col + x)

                nextToVisit.add(Node(new, node))
                visited.add(node.coordinates)
            }
        }

        throw IllegalStateException("Unsolveable maze 😭")
    }


    override fun part2(): Int {
        TODO()
    }
}
