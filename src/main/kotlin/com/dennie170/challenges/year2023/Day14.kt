package com.dennie170.challenges.year2023

import com.dennie170.Day
import com.dennie170.common.Tuple2
import com.dennie170.common.getMatrix

class Day14 : Day<Int>(2023, 14) {

    private lateinit var input: CharArray
    private lateinit var matrix: Array<Array<Char>>

    override fun setUp() {
        input = super.readInput().toCharArray().filter { it != '\n' }.toCharArray()
        matrix = getMatrix(input)
    }

    private fun getGrid(): Grid {
        val items = mutableListOf<GridItem>()

        for (row in matrix.indices) {
            for (col in matrix[0].indices) {
                val char = matrix[row][col]

                if (char == 'O') {
                    items.add(Rock(Location(row, col)))
                } else if (char == '#') {
                    items.add(Cube(Location(row, col)))
                }
            }
        }

        return Grid(items)
    }


    override fun part1(): Int {
        val grid = getGrid()

        return grid.gridItems.groupBy { it.location.col }.map {
            it.key to tiltColumnNorth(it.value)
        }.map { it.second }.flatten().sumOf { gridItem ->
            if (gridItem.type == Type.CUBE) {
                0
            } else {
                matrix.size - gridItem.location.row
            }
        }
    }


    override fun part2(): Int {
        return -1
        val grid = getGrid()

        val shuffled = shuffleGrid(grid, 1000000000)

        return -1
    }

    // Store the Direction + hashcode to see if we've been here before
    private val gridCache = hashSetOf<Tuple2<Direction, Int>>()

    private fun shuffleGrid(grid: Grid, times: Int): Grid {
        var unsafeGrid = grid

        var total = 0

        outer@ for (i in 0..<(times / 4)) {
            for (j in 0..<4) {
                unsafeGrid = tiltGridNorth(unsafeGrid)
//                unsafeGrid = rotateCounterClockwise(unsafeGrid)
                total++
            }
        }

        return unsafeGrid
    }

    private fun tiltGridNorth(grid: Grid): Grid {
        val items = grid.gridItems.groupBy { it.location.col }.map {
            it.key to tiltColumnNorth(it.value)
        }.map { it.second }.flatten().filter { it.type == Type.ROCK }.map { Rock(it.location) }

        val newGrid = Grid(items)

        gridCache.add(Tuple2(Direction.NORTH, newGrid.hashCode()))

        return newGrid
    }

    private fun tiltColumnNorth(items: List<GridItem>): List<GridItem> {
        val row = mutableListOf<GridItem>()

        var lastIndex = 0

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


    private fun rotateCounterClockwise(grid: Grid) {

    }

    data class Location(val row: Int, val col: Int)

    enum class Type {
        ROCK,
        CUBE
    }

    enum class Direction {
        NORTH,
        WEST,
        SOUTH,
        EAST
    }

    abstract class GridItem(val type: Type, val location: Location)

    class Rock(location: Location) : GridItem(Type.ROCK, location)
    class Cube(location: Location) : GridItem(Type.CUBE, location)

    data class Grid(val gridItems: List<GridItem>)
}
