package com.dennie170.challenges.year2025

import com.dennie170.Day
import com.dennie170.common.Coordinates

class Day7 : Day<Int>(2025, 7) {

    val input: String = readInput().trim()

    val matrix = input.lines().map { it.toCharArray() }.toTypedArray()


    override fun part1(): Int {
        val startCoordinates = Coordinates(0, matrix.first().indexOfFirst { it == 'S' })

        return followBeam(startCoordinates, hashSetOf()).count()
    }

    val visitedLocations = hashSetOf<Coordinates>()

    private fun followBeam(coordinates: Coordinates, acc: HashSet<Coordinates>): HashSet<Coordinates> {
        if (visitedLocations.contains(coordinates)) return acc
        if (coordinates.col - 1 < 0 || coordinates.col + 1 >= matrix.first().size) return acc

        visitedLocations.add(coordinates)

        for (row in coordinates.row + 1..<matrix.size) {
            if (matrix[row][coordinates.col] != '^') continue

            return acc.apply {
                add(Coordinates(row, coordinates.col))
                addAll(followBeam(Coordinates(row, coordinates.col - 1), acc) + followBeam(Coordinates(row, coordinates.col + 1), acc))
            }
        }

        return acc
    }

    override fun part2(): Int {
        TODO()
    }
}
