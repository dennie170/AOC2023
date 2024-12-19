package com.dennie170.challenges.year2024

import com.dennie170.Day

class Day19 : Day<Long>(2024, 19) {

    val lines: List<String> = readInput().lines()

    override fun part1(): Long {
        val availablePatterns = lines[0].split(", ").sortedByDescending { it.length }.toSet()
        val towels = lines.drop(2)

        return towels.count { findMatches(availablePatterns, it) > 0 }.toLong()
    }

    // Without cache this will probably run for days
    private val cache = mutableMapOf<String, Long>()

    private fun findMatches(availablePatterns: Set<String>, towel: String): Long {
        if (towel.isEmpty()) return 1L

        return cache.getOrPut(towel) {
            availablePatterns.filter { towel.startsWith(it) }
                .sumOf { findMatches(availablePatterns, towel.removePrefix(it)) }
        }
    }

    override fun part2(): Long {
        val availablePatterns = lines[0].split(", ").sortedByDescending { it.length }.toSet()
        val towels = lines.drop(2)

        return towels.sumOf { findMatches(availablePatterns, it) }
    }
}
