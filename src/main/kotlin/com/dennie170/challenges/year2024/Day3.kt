package com.dennie170.challenges.year2024

import com.dennie170.Day

class Day3 : Day<Int>(2024, 3) {

    val input: String = readInput()

    override fun part1(): Int {

        val regex = Regex("mul\\(([0-9]+,[0-9]+)\\)")

        val matches = regex.findAll(input)

        var total = 0

        for (match in matches) {
            val numbers = match.groups[1]!!.value.split(',').map { it.toInt() }

            total += (numbers[0] * numbers[1])
        }

        return total
    }

    override fun part2(): Int {
        TODO()
    }


}
