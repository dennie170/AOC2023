package com.dennie170.challenges.year2023

import com.dennie170.Day

class Day15 : Day<Int>(2023, 15) {

    private lateinit var input: List<CharArray>

    override fun setUp() {
        input = super.readInput().split(',').map { it.toCharArray() }
    }

    override fun part1(): Int {
        var result = 0

        for (instruction in input) {
            var instructionResult = 0
            for (char in instruction) {
                instructionResult += char.code
                instructionResult *= 17
                instructionResult %= 256
            }
            result += instructionResult
        }

        return result
    }

    override fun part2(): Int {
        TODO("Not yet implemented")
    }
}
