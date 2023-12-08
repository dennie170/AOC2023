package com.dennie170.challenges.year2023

import com.dennie170.Day
import com.dennie170.common.findLeastCommonMultiple


class Day8 : Day<Long>(2023, 8) {

    private lateinit var input: List<String>

    override fun setUp() {
        input = super.readInput().lines()
    }

    private val leftRight by lazy { input.first().toCharArray() }

    override fun part1(): Long {
        val instructions = input.drop(2).associate { line ->
            val key = line.substring(0, 3)
            val value = arrayOf(line.substring(7, 10), line.substring(12, 15))

            Pair(key, value)
        }

        return getStepsFromAtoZ(instructions, "AAA", "ZZZ")
    }

    private fun getStepsFromAtoZ(instructions: Map<String, Array<String>>, startNode: String, endNode: String): Long {
        var currentNode = startNode

        var steps = 0L

        var currentStep = 0
        while (true) {
            if (currentStep >= leftRight.size ) {
                currentStep = 0
            }

            steps++

            val (left, right) = instructions[currentNode]!!

            currentNode = if (leftRight[currentStep] == 'L') left else right

            if ((endNode == "Z" && currentNode[2] == 'Z') || currentNode == endNode) {
                break
            }

            currentStep++
        }

        return steps
    }

    override fun part2(): Long {
        val instructions = input.drop(2).associate { line ->
            val key = line.substring(0, 3)
            val value = arrayOf(line.substring(7, 10), line.substring(12, 15))

            Pair(key, value)
        }

        return instructions.keys.filter { it[2] == 'A' }.map {
            getStepsFromAtoZ(instructions, it, "Z")
        }.let(::findLeastCommonMultiple)
    }
}
