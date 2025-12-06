package com.dennie170.challenges.year2025

import com.dennie170.Day
import com.dennie170.common.isEmpty
import kotlin.collections.filterIndexed

class Day6 : Day<Long>(2025, 6) {

    val input: String = readInput().trim()

    override fun part1(): Long {
        val lines = input.lines()
        val numbers = lines.take(lines.size - 1).map { line ->
            line.split(' ').filter { it.isNotEmpty() }.map { it.toInt() }.toIntArray()
        }

        val operators = lines.last().split(' ').filter { it.isNotEmpty() }.map { it[0] }

        val sets = Array(numbers.first().size) {
            IntArray(numbers.size)
        }

        for ((lineNumber, line) in numbers.withIndex()) {
            for ((index, number) in line.withIndex()) {
                sets[index][lineNumber] = number
            }
        }


        return sets.zip(operators)
            .sumOf { (sum, op) ->
                sum.filterIndexed { index, _ -> index > 0 }.fold(sum.first().toLong()) { acc, i ->
                    when (op) {
                        '*' -> acc * i
                        '+' -> acc + i
                        else -> throw IllegalArgumentException()
                    }
                }
            }
    }

    override fun part2(): Long {
        val lines = input.lines()
        val numbers = lines.take(lines.size - 1).map { line ->
            line.toCharArray()
        }
        val longestLineSize = numbers.maxByOrNull { it.size }!!.size

        val operators = Regex("(\\*|\\+)(\\s+)").findAll(lines.last().padEnd(longestLineSize))
            .map {
                it.value.replace(' ', it.value.first())
            }
            .joinToString("")
            .toCharArray()
            .reversed()

        val sets = Array(longestLineSize) {
            CharArray(numbers.size)
        }


        for ((lineNumber, line) in numbers.withIndex()) {
            for ((index, number) in line.withIndex()) {
                sets[index][lineNumber] = number
            }
        }

        val sums = sets.reversed()
            .map { str ->  str.filter { !it.isEmpty() && !it.isWhitespace() }.joinToString("") }
            .zip(operators)
            .map { (sum, op) ->
                Pair(sum, op)
            }

        val groups = mutableListOf<Pair<Char, List<Long>>>()
        var currentGroup = mutableListOf<Long>()

        var currentOperator = sums.first().second

        for((sum, op) in sums) {
            if(sum.isEmpty()) {
                groups.add(Pair(currentOperator, currentGroup))
                currentGroup = mutableListOf()
                continue
            }

            currentGroup.add(sum.toLong())
            currentOperator = op
        }

        groups.add(Pair(currentOperator, currentGroup))

        return groups.sumOf { (op, sum) ->
            sum.filterIndexed { index, _ -> index > 0 }.fold(sum.first()) { acc, i ->
                when (op) {
                    '*' -> acc * i
                    '+' -> acc + i
                    else -> throw IllegalArgumentException()
                }
            }
        }
    }
}

