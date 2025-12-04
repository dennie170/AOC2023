package com.dennie170.challenges.year2025

import com.dennie170.Day
import java.util.*

class Day3 : Day<Long>(2025, 3) {

    val input: String = readInput().trim()

    override fun part1(): Long {
        val banks = input.lines().map { line -> line.toCharArray().map { char -> char.code - 48 } }

        return banks.sumOf { bank ->
            var firstBiggest = 0
            var firstIndex = 0

            for ((index, battery) in bank.withIndex()) {
                if (index == bank.size - 1) continue
                if (battery > firstBiggest) {
                    firstBiggest = battery
                    firstIndex = index
                }
            }

            var secondBiggest = 0

            for ((index, battery) in bank.withIndex()) {
                if (index <= firstIndex) continue
                if (battery > secondBiggest) {
                    secondBiggest = battery
                }
            }

            (firstBiggest.toString() + secondBiggest.toString()).toLong()
        }
    }

    override fun part2(): Long {
        val banks = input.lines().map { line -> line.toCharArray().map { char -> char.code - 48 } }

         return banks.map { bank ->
             bank.withIndex()
                 .sortedWith(compareBy({ it.value }, { it.index })).reversed().take(12).sortedBy { it.index }.map { it.value }
         }.sumOf {
             it.joinToString(separator = "") { list -> list.toString() }.toLong()
         }
    }
}
