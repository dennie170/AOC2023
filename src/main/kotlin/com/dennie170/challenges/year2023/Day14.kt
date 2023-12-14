package com.dennie170.challenges.year2023

import com.dennie170.Day
import com.dennie170.common.getMatrix
import com.dennie170.common.indexesOf

class Day14 : Day<Int>(2023, 14) {

    private lateinit var input: CharArray
    private lateinit var matrix: Array<Array<Char>>

    override fun setUp() {
        input = super.readInput().toCharArray().filter { it != '\n' }.toCharArray()
        matrix = getMatrix(input)
    }

    private fun getGrid(): Grid {
        val rocks = mutableListOf<Rock>()
        val cubes = mutableListOf<Cube>()

        for (row in matrix.indices) {
            for (col in matrix[0].indices) {
                val char = matrix[row][col]

                if (char == 'O') {
                    rocks.add(Rock(Location(row, col)))
                } else if (char == '#') {
                    cubes.add(Cube(Location(row, col)))
                }
            }
        }

        return Grid(rocks, cubes)
    }


    private fun tiltRowNorth(rocks: List<Rock>, cubes: List<Cube>): List<GridItem> {
        val items = (rocks + cubes).sortedBy { it.location.row }

        val cubeIndexes = items.indexesOf { it.type == Type.CUBE }

        val row = mutableListOf<GridItem>()

        var lastIndex: Int = 0

        for (i in items.indices) {
            val item = items[i]

            if (item.type == Type.CUBE) {
                row.add(item)
                lastIndex = item.location.row + 1
            } else {
                row.add(Rock(Location(lastIndex, item.location.col)))
                lastIndex++
            }
        }

        return row
    }

    override fun part1(): Int {
        val grid = getGrid()

        val cubesInColumns = grid.cubes.groupBy { it.location.col }

        return grid.rocks.groupBy { it.location.col }.map {
            it.key to tiltRowNorth(it.value, cubesInColumns[it.key] ?: listOf())
        }.map { it.second }.flatten().sumOf { gridItem ->
            if (gridItem.type == Type.CUBE) {
                0
            } else {
                matrix.size - gridItem.location.row
            }
        }
    }


    override fun part2(): Int {
        TODO("Not yet implemented")
    }

    data class Location(val row: Int, val col: Int)

    enum class Type {
        ROCK,
        CUBE
    }

    abstract class GridItem(val type: Type, val location: Location)

    class Rock(location: Location) : GridItem(Type.ROCK, location)
    class Cube(location: Location) : GridItem(Type.CUBE, location)

    data class Grid(val rocks: List<Rock>, val cubes: List<Cube>)
}
