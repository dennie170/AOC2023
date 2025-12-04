package com.dennie170.challenges.year2025

import com.dennie170.Day
import com.dennie170.common.Coordinates
import com.dennie170.common.getMatrix
import java.util.*

class Day4 : Day<Int>(2025, 4) {

    val input: String = readInput().trim()

    override fun part1(): Int {
        val matrix = getMatrix(input.toCharArray().filter { it != '\n' }.toCharArray())

        val paperRolls = HashSet<Coordinates>()

        for ((rowIndex, row) in matrix.withIndex()) {
            for ((colIndex, col) in row.withIndex()) {
                if (col == '@') paperRolls.add(Coordinates(rowIndex, colIndex))
            }
        }


        return paperRolls.count { roll ->
            rollCanBeRemoved(roll, matrix, paperRolls)
        }
    }

    private fun rollCanBeRemoved(
        roll: Coordinates,
        matrix: Array<Array<Char>>,
        paperRolls: Collection<Coordinates>
    ): Boolean {
        var adjacentRolls = 0

        val rows = (roll.row - 1..roll.row + 1)
        val columns = (roll.col - 1..roll.col + 1)

        for (row in rows) {
            for (col in columns) {
                // check out of bounds
                if (matrix.getOrNull(row) == null || matrix[row].getOrNull(col) == null || (row == roll.row && col == roll.col)) {
                    continue
                }

                if (paperRolls.contains(Coordinates(row, col))) {
                    adjacentRolls++
                }
            }

        }

        return adjacentRolls < 4
    }


    override fun part2(): Int {
        val matrix = getMatrix(input.toCharArray().filter { it != '\n' }.toCharArray())

        val paperRolls = HashSet<Coordinates>()

        for ((rowIndex, row) in matrix.withIndex()) {
            for ((colIndex, col) in row.withIndex()) {
                if (col == '@') paperRolls.add(Coordinates(rowIndex, colIndex))
            }
        }

        val startSize = paperRolls.size

        var rollsCanBeRemoved = true

        while(rollsCanBeRemoved) {
            var hasRemoved = false
            for(roll in paperRolls) {
                val res = rollCanBeRemoved(roll, matrix, paperRolls)

                if(res) {
                    paperRolls.remove(roll)
                    hasRemoved = true
                    break
                }
            }

            if(!hasRemoved) {
                rollsCanBeRemoved = false
            }

        }

        return startSize - paperRolls.size
    }
}
