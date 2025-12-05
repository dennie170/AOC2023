package com.dennie170.challenges.year2025

import com.dennie170.Day

class Day5 : Day<Int>(2025, 5) {

    val input: String = readInput().trim()

    override fun part1(): Int {
        val lines = input.split("\n\n")
        val freshRanges = lines[0].lines().map { line ->
            line.split('-').map { it.toLong() }.let { it[0]..it[1]}
        }

        val ingredients = lines[1].lines().map(String::toLong)

        return ingredients.count { ingredient ->
            freshRanges.any { it.contains(ingredient) }
        }
    }

    override fun part2(): Int {
        TODO()
    }
}
