package com.dennie170.challenges.year2024

import com.dennie170.Day
import com.dennie170.common.Coordinates
import com.dennie170.common.getMatrix

class Day12 : Day<Long>(2024, 12) {

    lateinit var input: String

    lateinit var matrix: Array<Array<Char>>

    override fun setUp() {
        input = super.readInput().replace("\n", "")
        matrix = getMatrix(input.toCharArray())
    }

    private data class Garden(val key: Char, val area: Int, val perimeter: Int)

    private fun checkGarden(key: Char, row: Int, col: Int, checkedCoordinates: MutableSet<Coordinates>): Set<Coordinates> {

        val adjacentPlants = mutableSetOf<Coordinates>()

        for (rowDiff in -1..1) {
            for (colDiff in -1..1) {
                // Skip current item
                if ((rowDiff == 0 && colDiff == 0) || (rowDiff != 0 && colDiff != 0)) continue

                // Check bounds
                if (row + rowDiff < 0 || row + rowDiff >= matrix.size) continue
                if (col + colDiff < 0 || col + colDiff >= matrix.size) continue

                if (checkedCoordinates.contains(Coordinates(row + rowDiff, col + colDiff))) continue

                // Only match same plants
                if (matrix[row + rowDiff][col + colDiff] != key) continue

                checkedCoordinates.add(Coordinates(row + rowDiff, col + colDiff))


                adjacentPlants.add(Coordinates(row + rowDiff, col + colDiff))

                adjacentPlants.addAll(checkGarden(key, row + rowDiff, col + colDiff, checkedCoordinates))
            }
        }

        return adjacentPlants
    }

    private fun calculateGardenSize(key: Char, adjacentPlants: Set<Coordinates>): Garden {

        val area = if (adjacentPlants.isNotEmpty()) adjacentPlants.size else 1

        var perimeter = if (adjacentPlants.isNotEmpty()) 0 else 4

        for (plant in adjacentPlants) {

            if (plant.row + 1 >= matrix.size) {
                perimeter++
            }

            if (plant.col + 1 >= matrix.size) {
                perimeter++
            }

            if (plant.row - 1 < 0) {
                perimeter++
            }

            if (plant.col - 1 < 0) {
                perimeter++
            }

            if (plant.row + 1 < matrix.size && matrix[plant.row + 1][plant.col] != key) {
                perimeter++
            }

            if (plant.row - 1 >= 0 && matrix[plant.row - 1][plant.col] != key) {
                perimeter++
            }

            if (plant.col + 1 < matrix.size && matrix[plant.row][plant.col + 1] != key) {
                perimeter++
            }

            if (plant.col - 1 >= 0 && matrix[plant.row][plant.col - 1] != key) {
                perimeter++
            }

        }

        return Garden(key, area, perimeter)
    }

    override fun part1(): Long {
        val gardens = mutableListOf<Garden>()

        val checkedCoordinates = mutableSetOf<Coordinates>()

        for (row in matrix.indices) {
            for (col in matrix.indices) {
                val coordinates = Coordinates(row, col)
                if (checkedCoordinates.contains(coordinates)) continue

                val adjacentPlants = checkGarden(matrix[row][col], row, col, checkedCoordinates)

                gardens.add(calculateGardenSize(matrix[row][col], adjacentPlants))

            }
        }

        return gardens.fold(0L) { acc, it ->
            acc + it.area * it.perimeter
        }
    }

    override fun part2(): Long {
        TODO()
    }
}
