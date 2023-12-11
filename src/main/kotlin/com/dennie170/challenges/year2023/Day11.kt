package com.dennie170.challenges.year2023

import com.dennie170.Day
import com.dennie170.common.getMatrix

class Day11 : Day<Int>(2023, 11) {

    private lateinit var input: String
    private lateinit var universe: Universe


    override fun setUp() {
        input = super.readInput().replace("\n", "")
        universe = getMatrix(input.toCharArray())
    }


    override fun part1(): Int {
        val expanded = expandUniverse(universe)
        val numerated = numerateAllGalaxies(expanded)

        TODO("Not yet implemented")
    }

    override fun part2(): Int {
        TODO("Not yet implemented")
    }

    /**
     * Walk over all rows and columns and expand if empty
     */
    private fun expandUniverse(universe: Universe): Universe {
        val expanded = universe.map { it.toMutableList() }.toMutableList()

        for (row in universe.indices) {
            // Row is empty
            if (universe[row].count { it == '#' } == 0) {
                expanded.add(row + 1, ".".repeat(expanded[row].size).toCharArray().toTypedArray().toMutableList())
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

    private fun numerateAllGalaxies(universe: Universe): Universe {
        var galaxy = 1

        for (row in universe.indices) {
            for (col in universe[row].indices) {
                if (universe[row][col] == '#') {
                    universe[row][col] = galaxy.toString().first()
                    galaxy++
                }
            }
        }

        return universe
    }

}

typealias Universe = Array<Array<Char>>
