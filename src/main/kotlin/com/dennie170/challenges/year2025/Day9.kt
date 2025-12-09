package com.dennie170.challenges.year2025

import com.dennie170.Day
import kotlin.math.abs

class Day9 : Day<Long>(2025, 9) {

    val input: String = readInput().trim()

    data class Tile(val row: Int, val col: Int)

    override fun part1(): Long {
        val tiles = input.lines().map {
            it.split(',').let { (col, row) -> Tile(row.toInt(), col.toInt()) }
        }

        var maxSurface = 0L

        for (tile in tiles) {
            for (b in tiles) {
                val surface = (abs(b.row - tile.row) + 1).toLong() * (abs(tile.col - b.col) + 1).toLong()

                if (surface > maxSurface) {
                    maxSurface = surface
                }
            }
        }

        return maxSurface
    }

    override fun part2(): Long {
        TODO()
    }
}
