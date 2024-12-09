package com.dennie170.challenges.year2024

import com.dennie170.Day
import com.dennie170.common.Coordinates
import com.dennie170.common.getMatrix
import kotlin.math.abs
import kotlin.math.max

class Day8 : Day<Int>(2024, 8) {
    lateinit var input: String
    lateinit var matrix: Array<Array<Char>>

    private val antennas: MutableMap<Char, MutableList<Coordinates>> = mutableMapOf()

    private var part = 1

    override fun setUp() {
        input = super.readInput().replace("\n", "")
        matrix = getMatrix(input.toCharArray())
    }

    private fun findAntennas() {
        antennas.clear()

        for (row in matrix.indices) {
            for (col in matrix.indices) {
                if (matrix[row][col] == '.') continue

                val list = antennas.getOrDefault(matrix[row][col], mutableListOf()).apply { add(Coordinates(row, col)) }

                antennas.putIfAbsent(matrix[row][col], list)
            }
        }
    }

    private fun antinodeCanBePlaced(coordinates: Coordinates, antennas: List<Coordinates>): Boolean {

        for (antennaA in antennas) {

            // loop over alle antennes in deze group behalve de huidige
            for (antennaB in antennas.filter { it.row != antennaA.row && it.col != antennaA.col }) {
                val rowDiff = abs(antennaA.row - antennaB.row)
                val colDiff = abs(antennaA.col - antennaB.col)


                // Antinode is in lijn met antenne a
                if (
                    (coordinates.row == antennaA.row - rowDiff && coordinates.col == antennaA.col - colDiff)
                    || (coordinates.row == antennaA.row + rowDiff && coordinates.col == antennaA.col + colDiff)
                    || (coordinates.row == antennaA.row + rowDiff && coordinates.col == antennaA.col - colDiff)
                    || (coordinates.row == antennaA.row - rowDiff && coordinates.col == antennaA.col + colDiff)
                ) {

                    // Antenne B is 2x zo ver weg als A
                    if (
                        (coordinates.row == antennaB.row - (rowDiff * 2) && coordinates.col == antennaB.col - (colDiff * 2))
                        || (coordinates.row == antennaB.row + (rowDiff * 2) && coordinates.col == antennaB.col + (colDiff * 2))
                        || (coordinates.row == antennaB.row + (rowDiff * 2) && coordinates.col == antennaB.col - (colDiff * 2))
                        || (coordinates.row == antennaB.row - (rowDiff * 2) && coordinates.col == antennaB.col + (colDiff * 2))
                    ) {
                        return true
                    }

                }
            }
        }

        return false
    }


    override fun part1(): Int {
        findAntennas()

        val antinodes = mutableSetOf<Coordinates>()


        for (antinodeRow in matrix.indices) {
            for (antinodeCol in matrix.indices) {

                for (row in matrix.indices) {
                    for (col in matrix.indices) {

                        if (antinodeCanBePlaced(Coordinates(antinodeRow, antinodeCol), antennas.getOrDefault(matrix[row][col], mutableListOf()))) {
                            antinodes.add(Coordinates(antinodeRow, antinodeCol))
                        }


                    }
                }
            }
        }

        return antinodes.size
    }

    private val placedAntinodes = mutableSetOf<Coordinates>()

    override fun part2(): Int {
        part = 2

        findAntennas()


        for ((key, antennaGroup) in antennas) {
            for (antennaA in antennaGroup) {

                // loop over alle antennes in deze group behalve de huidige
                for (antennaB in antennaGroup.filter { it.row != antennaA.row && it.col != antennaA.col }) {
                    val rowDiff = antennaA.row - antennaB.row
                    val colDiff = antennaA.col - antennaB.col


                    val steps = (matrix.size) / (max(rowDiff, colDiff))


                    var hasMatchingAntenna = false

                    // Walk down
                    for (step in 0..steps) {
                        val row = antennaB.row + rowDiff * step
                        val col = antennaB.col + colDiff * step

                        if (!matrix.indices.contains(row) || !matrix.indices.contains(col)) break

                        if (hasMatchingAntenna) {
                            placedAntinodes.add(Coordinates(row, col))
                            continue
                        }

                        if (matrix[row][col] == key) {
                            hasMatchingAntenna = true
                            placedAntinodes.add(Coordinates(row, col))
                            continue
                        }
                    }

                    hasMatchingAntenna = false

                    // Walk up
                    for (step in 0..steps) {
                        val row = antennaB.row - rowDiff * step
                        val col = antennaB.col - colDiff * step

                        if (!matrix.indices.contains(row) || !matrix.indices.contains(col)) break

                        if (hasMatchingAntenna) {
                            placedAntinodes.add(Coordinates(row, col))
                            continue
                        }

                        if (matrix[row][col] == key) {
                            hasMatchingAntenna = true
                            continue
                        }
                    }
                }

            }
        }

        return placedAntinodes.size
    }
}

