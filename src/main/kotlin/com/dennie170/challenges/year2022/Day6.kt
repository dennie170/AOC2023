package com.dennie170.challenges.year2022

import com.dennie170.Day

class Day6 : Day<Int>(2022, 6) {

    private lateinit var input: List<Char>

    override fun setUp() {
        input = super.readInput().toCharArray().toList()
    }

    private fun solve(length: Int): Int {
        for (index in 0..input.size) {
            if (input.subList(index, index + length).toSet().size == length) return index + length
        }

        return -1
    }

    override fun part1(): Int {
        return solve(4)
    }

    override fun part2(): Int {
        return solve(14)
    }
}
