package com.dennie170.challenges.year2024

import com.dennie170.Day

class Day7 : Day<Long>(2024, 7) {

    val lines: List<String> = readInput().lines()

    private var part = 1

    private data class Equation(val answer: Long, val numbers: List<Long>)

    private fun parseLine(line: String): Equation {
        val answer = line.substringBefore(':').toLong()
        val numbers = line.substringAfter(':').split(' ').mapNotNull {
            val trimmed = it.trim()
            trimmed.ifEmpty { null }
        }.map { it.toLong() }

        return Equation(answer, numbers)
    }

    private fun possibilities(current: List<Char>, remaining: Int): List<List<Char>> {
        if (remaining == 0) return listOf(current)

        return listOf(
            possibilities(current + '+', remaining - 1),
            possibilities(current + '*', remaining - 1),
            if (part == 2) possibilities(current + '|', remaining - 1) else emptyList(),
        ).flatten()
    }

    private fun calculatePossibilitiesForEquation(equation: Equation): Long {
        val possibilities = possibilities(emptyList(), equation.numbers.size - 1)

        for (operators in possibilities) {

            val sum = mutableListOf<Any>()

            for ((index, number) in equation.numbers.withIndex()) {
                sum.add(number)

                if (index < operators.size) {
                    sum.add(operators[index])
                }
            }


            var total = 0L
            var operator = '+'

            for ((index, item) in sum.withIndex()) {
                if (total > equation.answer) break

                if (index == 0) {
                    total = item as Long
                    continue
                }

                if (item is Char) {
                    operator = item
                    continue
                }

                if (item is Long) {
                    when (operator) {
                        '*' -> {
                            total *= item
                        }

                        '+' -> {
                            total += item
                        }

                        '|' -> {
                            total = "$total$item".toLong()
                        }

                        else -> throw IllegalStateException("Illegal operator '$operator'")
                    }
                }
            }

            if (total == equation.answer) {
                return equation.answer
            }
        }

        return 0
    }

    override fun part1(): Long {
        val equations = lines.map { parseLine(it) }


        return equations.sumOf { calculatePossibilitiesForEquation(it) }
    }

    override fun part2(): Long {
        part = 2

        val equations = lines.map { parseLine(it) }


        return equations.sumOf { calculatePossibilitiesForEquation(it) }
    }
}

