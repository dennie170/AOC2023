package com.dennie170.challenges.year2025

import com.dennie170.Day
import com.dennie170.common.areSomewhatOverlapping
import java.util.*
import kotlin.math.max
import kotlin.math.min

class Day5 : Day<Long>(2025, 5) {

    val input: String = readInput().trim()

    override fun part1(): Long {
        val lines = input.split("\n\n")
        val freshRanges = lines[0].lines().map { line ->
            line.split('-').map { it.toLong() }.let { it[0]..it[1] }
        }

        val ingredients = lines[1].lines().map(String::toLong)

        return ingredients.count { ingredient ->
            freshRanges.any { it.contains(ingredient) }
        }.toLong()
    }

    override fun part2(): Long {
        val ranges = LinkedList(input.substringBefore("\n\n").lines().map { line ->
            line.split('-').map { it.toLong() }.let { it[0]..it[1] }
        })

        val nonOverlappingRanges = mutableListOf<LongRange>()

        while (ranges.isNotEmpty()) {
            var range = ranges.pop()

            val overlappingRange = ranges.firstOrNull { LongRange.areSomewhatOverlapping(range, it) }

            if (overlappingRange != null) {
                range = LongRange(
                    min(range.first, overlappingRange.first),
                    max(range.last, overlappingRange.last),
                )

                ranges.remove(overlappingRange)

                ranges.add(range)
            } else {
                nonOverlappingRanges.add(range)
            }
        }

        return nonOverlappingRanges.sumOf {
            (it.last - it.first) + 1
        }
    }

}
