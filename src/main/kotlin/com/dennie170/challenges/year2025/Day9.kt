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

        return tiles.maxOf { tile ->
            tiles.maxOf { b ->
                (abs(b.row - tile.row) + 1).toLong() * (abs(tile.col - b.col) + 1).toLong()
            }
        }
    }

    override fun part2(): Long {
        TODO()
    }
}
