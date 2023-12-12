package com.dennie170.challenges.year2023

import com.dennie170.Day
import com.dennie170.common.getMatrix
import kotlin.math.abs
import kotlin.math.exp

class Day11 : Day<Int>(2023, 11) {

    private lateinit var input: String

    override fun setUp() {
        input = super.readInput().replace("\n", "")
    }


    override fun part1(): Int {
        val universe = prepareUniverse(getMatrix(input.toCharArray()))
        val galaxies = getGalaxyPositions(universe)

        val pairs = galaxies.map { galaxy ->
            val id = galaxy.key

            var distance = 0

            for (i in ((id)..galaxies.size)) {
                distance += getDistanceBetweenGalaxies(galaxies[id]!!, galaxies[i]!!)
            }

            distance
        }

        return pairs.sum()
    }

    /**
     * Manhattan distance between points
     */
    private fun getDistanceBetweenGalaxies(a: Position, b: Position): Int {
        return (abs(a.row - b.row) + abs(a.col - b.col))
    }

    override fun part2(): Int {
        TODO("Not yet implemented")
    }

    private fun getGalaxyPositions(universe: NumeratedUniverse): HashMap<Int, Position> {
        val locations = hashMapOf<Int, Position>()
        var galaxyToFind = 1

        for (row in universe.indices) {
            for (col in universe[row].indices) {
                // Check if we found a galaxy in the current column
                if (universe[row][col] == galaxyToFind) {
                    locations[galaxyToFind] = Position(row, col)
                    galaxyToFind++
                }
            }
        }

        return locations
    }


    /**
     * Walk over all rows and columns and expand if empty
     */
    private fun expandUniverse(universe: Universe): Universe {
        val expanded = universe.map { it.toMutableList() }.toMutableList()

        var addedRows = 0
        for (row in expanded.indices) {
            // Row is empty
            if (universe[row].count { it == '#' } == 0) {
                expanded.add(row + addedRows, ".".repeat(expanded[row + addedRows].size).toCharArray().toTypedArray().toMutableList())
                addedRows++
            }
        }

        var added = 0

        // Loop over all columns
        for (col in expanded[0].indices) {

            val columns = mutableListOf<Char>()

            for (row in expanded.indices) {
                columns.add(expanded[row][col + added])
            }

            // This column is empty
            if (columns.count { it == '#' } == 0) {
                for (row in expanded.indices) {
                    expanded[row].add(col + added, '.')
                }

                added++
            }

        }

        return expanded.map { it.toTypedArray() }.toTypedArray()
    }

    private fun numerateAllGalaxies(universe: Universe): NumeratedUniverse {
        var galaxy = 1
        val numerated = Array(universe.size) { Array(universe[0].size) { 0 } }

        for (row in universe.indices) {
            for (col in universe[row].indices) {
                if (universe[row][col] == '#') {

                    numerated[row][col] = galaxy

                    galaxy++
                } else {
                    numerated[row][col] = 0
                }
            }
        }

        return numerated
    }

    private fun prepareUniverse(universe: Universe) = numerateAllGalaxies(expandUniverse(universe))

    data class Position(val row: Int, val col: Int)
}

typealias Universe = Array<Array<Char>>
typealias NumeratedUniverse = Array<Array<Int>>
