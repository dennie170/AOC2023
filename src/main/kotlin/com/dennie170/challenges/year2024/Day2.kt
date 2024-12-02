package com.dennie170.challenges.year2024

import com.dennie170.Day
import com.dennie170.common.areAllDecreasing
import com.dennie170.common.areAllIncreasing
import com.dennie170.common.containsDuplicates
import com.dennie170.common.maxDifferenceBetweenItems

class Day2 : Day<Int>(2024, 2) {

    val lines: List<String> = readInput().lines()

    override fun part1(): Int {
        val reports = lines.map { it.split(' ') }.map {
            it.map { r -> r.toInt() }
        }

       return reports.filter {
           (it.areAllIncreasing() || it.areAllDecreasing())
                   && !it.containsDuplicates()
                   && it.maxDifferenceBetweenItems() <= 3

       }.size
    }


    override fun part2(): Int {
        return -1
    }
}
