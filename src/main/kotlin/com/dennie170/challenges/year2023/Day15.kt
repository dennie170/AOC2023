package com.dennie170.challenges.year2023

import com.dennie170.Day

class Day15 : Day<Int>(2023, 15) {

    private lateinit var input: List<CharArray>

    override fun setUp() {
        input = super.readInput().split(',').map { it.toCharArray() }
    }

    override fun part1(): Int {
        var result = 0

        for (instruction in input) {
            var instructionResult = 0
            for (char in instruction) {
                instructionResult += char.code
                instructionResult *= 17
                instructionResult %= 256
            }
            result += instructionResult
        }

        return result
    }

    private val boxes = mutableMapOf<Int, MutableList<Lens>>()

    data class Lens(val code: String, val focalLength: Int)

    override fun part2(): Int {

        for (instruction in input) {

            val operator = if (instruction.contains('=')) '=' else '-'

            val operatorIndex = instruction.indexOf(operator)
            val code = instruction.slice(0..<operatorIndex).toCharArray()
            val box = hash(code)

            if (operator == '=') {
                // Add lens to box
                val focalLength = String(instruction.slice(operatorIndex + 1..<instruction.size).toCharArray()).toInt()


                if (boxes.containsKey(box)) {
                    val lens = boxes[box]!!.indexOfFirst { it.code == String(code) }

                    if (lens == -1) {
                        boxes[box]!!.add(Lens(String(code), focalLength))

                    } else {
                        boxes[box]!![lens] = boxes[box]!![lens].copy(focalLength = focalLength)
                    }

                } else {
                    boxes[box] = mutableListOf(Lens(String(code), focalLength))
                }
            } else {

                val lenses = boxes[box] ?: continue

                val lens = lenses.find { it.code == String(code) } ?: continue

                boxes[box]!!.remove(lens)
            }
        }

        return boxes.map {
            var value = 0

            for (index in it.value.indices) {
                val lens = it.value[index]
                value += (it.key + 1) * (index + 1) * lens.focalLength
            }

            value
        }.sum()
    }

    private fun hash(instruction: CharArray): Int {
        var instructionResult = 0
        for (char in instruction) {
            instructionResult += char.code
            instructionResult *= 17
            instructionResult %= 256
        }

        return instructionResult
    }
}
