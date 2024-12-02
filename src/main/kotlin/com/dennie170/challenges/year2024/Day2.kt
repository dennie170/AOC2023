package com.dennie170.challenges.year2024

import com.dennie170.Day
import com.dennie170.common.areAllDecreasing
import com.dennie170.common.areAllIncreasing
import com.dennie170.common.containsDuplicates
import com.dennie170.common.maxDifferenceBetweenItems

class Day2 : Day<Int>(2024, 2) {

    val lines: List<String> = readInput().lines()


    private fun parseReports() = lines.map { it.split(' ') }.map {
        it.map { r -> r.toInt() }
    }

    override fun part1(): Int {
        val reports = parseReports()

        return reports.filter {
            (it.areAllIncreasing() || it.areAllDecreasing())
                    && !it.containsDuplicates()
                    && it.maxDifferenceBetweenItems() <= 3

        }.size
    }

    override fun part2(): Int {
        val reports = parseReports()

        return reports.filter {
            bruteForceSafety(it, null)
        }.size
    }

    private fun bruteForceSafety(report: List<Int>, dropIndex: Int? = null): Boolean {
        val r = if (dropIndex == null) report else report.toMutableList().apply {
            if (dropIndex < size) removeAt(dropIndex)
        }.toList()

        // Regular safety
        if ((r.areAllIncreasing() || r.areAllDecreasing())
            && !r.containsDuplicates()
            && r.maxDifferenceBetweenItems() <= 3
        ) {

            return true
        }

        if (dropIndex == null) {
            for (i in 0..r.size) {
                if (bruteForceSafety(r, i)) {
                    return true
                }
            }
        }

        return false
    }

}
