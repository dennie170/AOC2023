package com.dennie170.challenges.year2022

import com.dennie170.Day

class Day1 : Day<Int>(2022, 1) {

    private lateinit var input: String

    override fun setUp() {
        input = super.readInput()
    }

    private fun getElves(): List<Int> {
        return input.split("\n\n").map {
            it.lineSequence().map { line -> line.trim().toInt() }.toList()
        }.map { it.fold(0) { acc, i -> acc + i } }
    }

    override fun part1(): Int {
        return getElves().max()
    }

    override fun part2(): Int {
        return getElves().sortedByDescending { it }.take(3).reduce { acc, i -> acc + i }
    }
}
