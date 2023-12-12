package com.dennie170.challenges.year2023

import com.dennie170.Day
import com.dennie170.common.getMatrix
import kotlin.math.abs
import kotlin.math.exp

class Day11 : Day<Long>(2023, 11) {

    companion object {
        const val EXPANSION_SIZE = 1_000_000
    }

    private lateinit var input: String

    override fun setUp() {
        input = super.readInput().replace("\n", "")
    }


    override fun part1(): Long {
        val universe = prepareUniverse(getMatrix(input.toCharArray()))
        val galaxies = getGalaxyPositions(universe)

        val pairs = galaxies.map { galaxy ->
            val id = galaxy.key

            var distance = 0L

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
    private fun getDistanceBetweenGalaxies(a: Position, b: Position): Long {
        return (abs(a.row - b.row) + abs(a.col - b.col))
    }

    private fun getGalaxyPositions(universe: NumeratedUniverse): HashMap<Int, Position> {
        val locations = hashMapOf<Int, Position>()
        var galaxyToFind = 1

        for (row in universe.indices) {
            for (col in universe[row].indices) {
                // Check if we found a galaxy in the current column
                if (universe[row][col] == galaxyToFind) {
                    locations[galaxyToFind] = Position(row.toLong(), col.toLong())
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

    data class Position(val row: Long, val col: Long)


    override fun part2(): Long {
        val universe = getMatrix(input.toCharArray())
        var galaxies = getGalaxyPositions(numerateAllGalaxies(universe))


        // Expand all rows by a million
        var addedRows = 0
        for (row in universe.indices) {
            if (universe[row].none { it == '#' }) {
                for (galaxy in galaxies) {
                    if (galaxy.value.row > (row + addedRows)) {
                        galaxies[galaxy.key] = Position(galaxies[galaxy.key]!!.row + EXPANSION_SIZE, galaxies[galaxy.key]!!.col)
                    }
                }
                addedRows += EXPANSION_SIZE
            }
        }

        var addedCols = 0
        for (col in universe[0].indices) {

            val columns = mutableListOf<Char>()

            for (row in universe.indices) {
                columns.add(universe[row][col])
            }

            // This column is empty
            if (columns.none { it == '#' }) {
                for (galaxy in galaxies) {
                    if (galaxy.value.col > (col + addedCols)) {
                        galaxies[galaxy.key] = Position(galaxies[galaxy.key]!!.row, galaxies[galaxy.key]!!.col + EXPANSION_SIZE)
                    }
                }
                addedCols += EXPANSION_SIZE
            }

        }

        val pairs = galaxies.map { galaxy ->
            val id = galaxy.key

            var distance = 0L

            for (i in ((id)..galaxies.size)) {
                distance += getDistanceBetweenGalaxies(galaxies[id]!!, galaxies[i]!!)
            }

            distance
        }


        return pairs.sum()
    }
    // 1100647098 -> too low
    // 1100552268 -> too low
    // 1099821032 -> too low
    // 731244992588 -> Wrong?
}

typealias Universe = Array<Array<Char>>
typealias NumeratedUniverse = Array<Array<Int>>
