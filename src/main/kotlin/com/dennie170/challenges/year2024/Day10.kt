package com.dennie170.challenges.year2024

import com.dennie170.Day
import com.dennie170.common.Coordinates
import com.dennie170.common.getMatrix

class Day10 : Day<Int>(2024, 10) {

    lateinit var input: String
    lateinit var matrix: Array<IntArray>

    override fun setUp() {
        input = super.readInput().replace("\n", "")
        matrix = getMatrix(input.toCharArray().map { if(it == '.') -1 else it.digitToInt() })
    }

    private val reachedPeaks = mutableSetOf<Pair<Coordinates, Coordinates>>()

    private val trialHeads = mutableListOf<Pair<Coordinates, Int>>()

    private val trialHeadScores = mutableMapOf<Coordinates, Int>()


    // Recursive function to walk the trial
    // Coordinates do not need to be a 0. Can also pick up a new trail
    private fun walkTrial(trialHead: Coordinates, coordinates: Coordinates = trialHead) {
        val currentValue = matrix[coordinates.row][coordinates.col]

        if (coordinates.row + 1 < matrix.size && matrix[coordinates.row + 1][coordinates.col] == currentValue + 1) {
            if (matrix[coordinates.row + 1][coordinates.col] == 9) {


                if (!reachedPeaks.contains(trialHead to coordinates.copy(row = coordinates.row + 1))) {
                    reachedPeaks.add(trialHead to coordinates.copy(row = coordinates.row + 1))

                    val trialHeadIndex = trialHeads.indexOfFirst { it.first == trialHead }
                    val head = trialHeads[trialHeadIndex]
                    trialHeads[trialHeadIndex] = head.copy(second = head.second + 1)
                    trialHeadScores[trialHead] = trialHeadScores[trialHead]!! + 1
                } else {
                    trialHeadScores[trialHead] = trialHeadScores[trialHead]!! + 1
                }

            } else walkTrial(trialHead, Coordinates(coordinates.row + 1, coordinates.col))
        }


        if (coordinates.row - 1 >= 0 && matrix[coordinates.row - 1][coordinates.col] == currentValue + 1) {
            if (matrix[coordinates.row - 1][coordinates.col] == 9) {

                if (!reachedPeaks.contains(trialHead to coordinates.copy(row = coordinates.row - 1))) {
                    reachedPeaks.add(trialHead to coordinates.copy(row = coordinates.row - 1))

                    val trialHeadIndex = trialHeads.indexOfFirst { it.first == trialHead }
                    val head = trialHeads[trialHeadIndex]
                    trialHeads[trialHeadIndex] = head.copy(second = head.second + 1)
                    trialHeadScores[trialHead] = trialHeadScores[trialHead]!! + 1
                } else {
                    trialHeadScores[trialHead] = trialHeadScores[trialHead]!! + 1
                }

            } else walkTrial(trialHead, Coordinates(coordinates.row - 1, coordinates.col))
        }


        if (coordinates.col + 1 < matrix.size && matrix[coordinates.row][coordinates.col + 1] == currentValue + 1) {
            if (matrix[coordinates.row][coordinates.col + 1] == 9) {

                if (!reachedPeaks.contains(trialHead to coordinates.copy(col = coordinates.col + 1))) {
                    reachedPeaks.add(trialHead to coordinates.copy(col = coordinates.col + 1))

                    val trialHeadIndex = trialHeads.indexOfFirst { it.first == trialHead }
                    val head = trialHeads[trialHeadIndex]
                    trialHeads[trialHeadIndex] = head.copy(second = head.second + 1)
                    trialHeadScores[trialHead] = trialHeadScores[trialHead]!! + 1
                } else {
                    trialHeadScores[trialHead] = trialHeadScores[trialHead]!! + 1
                }


            } else walkTrial(trialHead, Coordinates(coordinates.row, coordinates.col + 1))
        }

        if (coordinates.col - 1 >= 0 && matrix[coordinates.row][coordinates.col - 1] == currentValue + 1) {
            if (matrix[coordinates.row][coordinates.col - 1] == 9) {

                if (!reachedPeaks.contains(trialHead to coordinates.copy(col = coordinates.col - 1))) {
                    reachedPeaks.add(trialHead to coordinates.copy(col = coordinates.col - 1))

                    val trialHeadIndex = trialHeads.indexOfFirst { it.first == trialHead }
                    val head = trialHeads[trialHeadIndex]
                    trialHeads[trialHeadIndex] = head.copy(second = head.second + 1)
                    trialHeadScores[trialHead] = trialHeadScores[trialHead]!! + 1
                } else {
                    trialHeadScores[trialHead] = trialHeadScores[trialHead]!! + 1
                }

            } else walkTrial(trialHead, Coordinates(coordinates.row, coordinates.col - 1))
        }

    }

    override fun part1(): Int {

        for (row in matrix.indices) {
            for (col in matrix.indices) {
                if (matrix[row][col] != 0) continue // Only filter on starts

                trialHeads.add(Pair(Coordinates(row, col), 0))
                trialHeadScores[Coordinates(row, col)] = 0

                walkTrial(Coordinates(row, col))
            }
        }

        return trialHeads.sumOf { it.second }
    }

    override fun part2(): Int {

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
