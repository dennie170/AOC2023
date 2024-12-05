package com.dennie170.challenges.year2024

import com.dennie170.Day

class Day1 : Day<Int>(2024, 1) {

    val lines: List<String> = readInput().lines()

    private fun parseLine(line: String): Pair<Int, Int> {
        return line.substring(0..4).toInt() to line.substring(8..12).toInt()
    }

    override fun part1(): Int {
        val pairs = lines.map { parseLine(it) }
        val left = pairs.map { it.first }.sorted()
        val right = pairs.map { it.second }.sorted()

        return left.zip(right).sumOf {
            if(it.first > it.second) {
                it.first - it.second
            } else it.second - it.first
        }
    }

    override fun part2(): Int {
        val pairs = lines.map { parseLine(it) }
        val left = pairs.map { it.first }
        val right = mutableMapOf<Int, Int>()

        for(p in pairs) {
            right[p.second] = right[p.second]?.or(0)?.plus(1) ?: 1
        }

        return left.sumOf {
            right[it]?.or(0)?.times(it) ?: 0
        }
    }
}
