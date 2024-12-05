package com.dennie170.challenges.year2024

import com.dennie170.Day

class Day3 : Day<Int>(2024, 3) {

    val input: String = readInput()


    override fun part1(): Int {
        return Regex("mul\\((\\d+),(\\d+)\\)").findAll(input).sumOf { match ->
            val first = match.groups[1]!!.value.toInt()
            val second = match.groups[2]!!.value.toInt()

            first * second
        }
    }

    override fun part2(): Int {
        var enabled = true
        var total = 0

        val matches = Regex("don't\\(\\)|mul\\(([0-9]+),([0-9]+)\\)|do\\(\\)").findAll(input)

        for (match in matches) {
            if (match.groups[0]!!.value == "don't()") {
                enabled = false
                continue
            }

            if (match.groups[0]!!.value == "do()") {
                enabled = true
                continue
            }

            if (enabled) {
                val first = match.groups[1]!!.value.toInt()
                val second = match.groups[2]!!.value.toInt()

                total += first * second
            }
        }

        return total
    }
}
