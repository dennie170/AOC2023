package com.dennie170.challenges.year2024

import com.dennie170.Day
import com.dennie170.common.Coordinates
import com.dennie170.common.getMatrix

class Day10 : Day<Int>(2024, 10) {

    lateinit var matrix: Array<IntArray>

    override fun setUp() {
        val input = super.readInput().replace("\n", "")
        matrix = getMatrix(input.toCharArray().map { if (it == '.') -1 else it.digitToInt() })
    }

    private val reachedPeaks = mutableSetOf<Pair<Coordinates, Coordinates>>()

    private val trailHeads = mutableListOf<Pair<Coordinates, Int>>()

    private val trailHeadScores = mutableMapOf<Coordinates, Int>()

    // Recursive function to walk the trail
    // Coordinates do not need to be a 0. Can also pick up a new trail
    private fun walkTrail(trailHead: Coordinates, coordinates: Coordinates = trailHead) {
        val currentValue = matrix[coordinates.row][coordinates.col]

        for (rowDiff in -1..1) {
            for (colDiff in -1..1) {
                if ((rowDiff == 0 && colDiff == 0) || (rowDiff != 0 && colDiff != 0)) continue

                if (coordinates.row + rowDiff < 0 || coordinates.row + rowDiff >= matrix.size) continue
                if (coordinates.col + colDiff < 0 || coordinates.col + colDiff >= matrix.size) continue

                if (matrix[coordinates.row + rowDiff][coordinates.col + colDiff] != currentValue + 1) continue

                if (matrix[coordinates.row + rowDiff][coordinates.col + colDiff] == 9) {
                    if (!reachedPeaks.contains(trailHead to coordinates.copy(row = coordinates.row + rowDiff, col = coordinates.col + colDiff))) {
                        reachedPeaks.add(trailHead to coordinates.copy(row = coordinates.row + rowDiff, col = coordinates.col + colDiff))

                        val trailHeadIndex = trailHeads.indexOfFirst { it.first == trailHead }
                        val head = trailHeads[trailHeadIndex]
                        trailHeads[trailHeadIndex] = head.copy(second = head.second + 1)
                    }
                    if (part == 2) {
                        trailHeadScores[trailHead] = trailHeadScores[trailHead]!! + 1
                    }

                } else walkTrail(trailHead, Coordinates(coordinates.row + rowDiff, coordinates.col + colDiff))
            }
        }
    }

    private fun solve(): Int {
        trailHeads.clear()
        trailHeadScores.clear()
        reachedPeaks.clear()

        for (row in matrix.indices) {
            for (col in matrix.indices) {
                if (matrix[row][col] != 0) continue // Only filter on starts

                trailHeads.add(Pair(Coordinates(row, col), 0))
                if (part == 2) {
                    trailHeadScores[Coordinates(row, col)] = 0
                }

                walkTrail(Coordinates(row, col))
            }
        }

        return if (part == 1) trailHeads.sumOf { it.second } else trailHeadScores.map { it.value }.sum()
    }

    private var part = 1
    override fun part1(): Int {
        return solve()
    }

    override fun part2(): Int {
        part = 2

        return solve()
    }
}
