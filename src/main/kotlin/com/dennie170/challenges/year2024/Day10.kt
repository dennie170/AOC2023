package com.dennie170.challenges.year2024

import com.dennie170.Day
import com.dennie170.common.Coordinates
import com.dennie170.common.getMatrix

class Day10 : Day<Int>(2024, 10) {

    lateinit var input: String
    lateinit var matrix: Array<IntArray>

    override fun setUp() {
        input = super.readInput().replace("\n", "")
        matrix = getMatrix(input.toCharArray().map { if (it == '.') -1 else it.digitToInt() })
    }

    private val reachedPeaks = mutableSetOf<Pair<Coordinates, Coordinates>>()

    private val trialHeads = mutableListOf<Pair<Coordinates, Int>>()

    private val trialHeadScores = mutableMapOf<Coordinates, Int>()

    // Recursive function to walk the trial
    // Coordinates do not need to be a 0. Can also pick up a new trail
    private fun walkTrial(trialHead: Coordinates, coordinates: Coordinates = trialHead) {
        val currentValue = matrix[coordinates.row][coordinates.col]

        for (rowDiff in -1..1) {
            for (colDiff in -1..1) {
                if ((rowDiff == 0 && colDiff == 0) || (rowDiff != 0 && colDiff != 0)) continue

                if (coordinates.row + rowDiff < 0 || coordinates.row + rowDiff >= matrix.size) continue
                if (coordinates.col + colDiff < 0 || coordinates.col + colDiff >= matrix.size) continue

                if (matrix[coordinates.row + rowDiff][coordinates.col + colDiff] != currentValue + 1) continue

                if (matrix[coordinates.row + rowDiff][coordinates.col + colDiff] == 9) {
                    if (!reachedPeaks.contains(trialHead to coordinates.copy(row = coordinates.row + rowDiff, col = coordinates.col + colDiff))) {
                        reachedPeaks.add(trialHead to coordinates.copy(row = coordinates.row + rowDiff, col = coordinates.col + colDiff))

                        val trialHeadIndex = trialHeads.indexOfFirst { it.first == trialHead }
                        val head = trialHeads[trialHeadIndex]
                        trialHeads[trialHeadIndex] = head.copy(second = head.second + 1)
                    }
                    if (part == 2) {
                        trialHeadScores[trialHead] = trialHeadScores[trialHead]!! + 1
                    }

                } else walkTrial(trialHead, Coordinates(coordinates.row + rowDiff, coordinates.col + colDiff))
            }
        }
    }

    private var part = 1
    override fun part1(): Int {

        for (row in matrix.indices) {
            for (col in matrix.indices) {
                if (matrix[row][col] != 0) continue // Only filter on starts

                trialHeads.add(Pair(Coordinates(row, col), 0))
                walkTrial(Coordinates(row, col))
            }
        }

        return trialHeads.sumOf { it.second }
    }

    override fun part2(): Int {
        part = 2

        trialHeads.clear()
        trialHeadScores.clear()
        reachedPeaks.clear()

        for (row in matrix.indices) {
            for (col in matrix.indices) {
                if (matrix[row][col] != 0) continue // Only filter on starts

                trialHeads.add(Pair(Coordinates(row, col), 0))
                trialHeadScores[Coordinates(row, col)] = 0

                walkTrial(Coordinates(row, col))
            }
        }

        return trialHeadScores.map { it.value }.sum()
    }
}
