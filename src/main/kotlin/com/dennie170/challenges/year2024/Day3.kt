package com.dennie170.challenges.year2024

import com.dennie170.Day

class Day3 : Day<Int>(2024, 3) {

    val input: String = readInput()


    override fun part1(): Int {
        val matches = Regex("mul\\(([0-9]+,[0-9]+)\\)").findAll(input)

        var total = 0

        for (match in matches) {
            val numbers = match.groups[1]!!.value.split(',').map { it.toInt() }

            total += (numbers[0] * numbers[1])
        }

        return total
    }

    override fun part2(): Int {
        val operations = input.split("don't()")

        var enabled = true
        var total = 0

        for (op in operations) {
            val matches = Regex("mul\\(([0-9]+,[0-9]+)\\)|do\\(\\)").findAll(op)
            for (match in matches) {
                if (match.groups[0]!!.value == "do()") {
                    enabled = true
                    continue
                }

                if (enabled) {
                    val numbers = match.groups[1]!!.value.split(',').map { it.toInt() }

                    total += (numbers[0] * numbers[1])
                }
            }

            enabled = false
        }

        return total
    }


}
