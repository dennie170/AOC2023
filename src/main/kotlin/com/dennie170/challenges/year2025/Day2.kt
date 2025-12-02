package com.dennie170.challenges.year2025

import com.dennie170.Day
import com.dennie170.common.splitInHalf

class Day2 : Day<Long>(2025, 2) {

    val input: String = readInput().trim()

    override fun part1(): Long {
        val ranges = input.split(',')
        var invalidCount = 0L

        for (range in ranges) {
            val (a, b) = range.split('-').map(String::toLong)

            for (number in a..b) {
                val str = number.toString()
                val split = str.splitInHalf()

                invalidCount += if (split.first() == split.last()) str.toLong() else 0
            }
        }

        return invalidCount
    }

    override fun part2(): Long {
        TODO()
    }
}
