package com.dennie170.challenges.year2024

import com.dennie170.Day
import com.dennie170.common.areAllDecreasing
import com.dennie170.common.areAllIncreasing
import com.dennie170.common.containsDuplicates
import com.dennie170.common.maxDifferenceBetweenItems

class Day2 : Day<Int>(2024, 2) {

    val lines: List<String> = readInput().lines()


    private fun parseReports(): List<MutableList<Int>> {
        return lines.map { it.split(' ') }.map {
            it.map { r -> r.toInt() }.toMutableList()
        }
    }

    private fun isValidReport(it: List<Int>): Boolean {
        return ((it.areAllIncreasing() || it.areAllDecreasing())
                && !it.containsDuplicates()
                && it.maxDifferenceBetweenItems() <= 3)
    }

    override fun part1(): Int {
        return parseReports().count(::isValidReport)
    }

    override fun part2(): Int {
        val reports = parseReports()

        return reports.count {
            bruteForceSafety(it, null)
        }
    }

    private fun bruteForceSafety(report: MutableList<Int>, dropIndex: Int? = null): Boolean {
        val r = if (dropIndex == null) report else report.apply {
            if (dropIndex < size) removeAt(dropIndex)
        }

        // Regular safety
        if (isValidReport(r)) {
            return true
        }

        if (dropIndex == null) {
            for (i in 0..r.size) {
                if (bruteForceSafety(r.toMutableList(), i)) {
                    return true
                }
            }
        }

        return false
    }

}
