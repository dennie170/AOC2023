package com.dennie170.challenges.year2025

import com.dennie170.Day
import kotlin.math.abs

class Day1 : Day<Int>(2025, 1) {

    enum class Direction {
        LEFT,
        RIGHT
    }

    data class Instruction(val direction: Direction, val amount: Int)

    val lines: List<String> = readInput().lines()


    override fun part1(): Int {
        var currentPosition = 50
        var timesOnZero = 0

        val instructions = lines.map {
            val direction = if (it.first() == 'L') Direction.LEFT else Direction.RIGHT
            val amount = it.substring(1).toInt()
            Instruction(direction, amount)
        }

        for (instruction in instructions) {
            var next = currentPosition

            next = if (instruction.direction == Direction.LEFT) {
                (next - instruction.amount) % 100
            } else {
                (next + instruction.amount) % 100
            }

            if (next == 0) {
                timesOnZero++
            }

            currentPosition = next
        }

        return timesOnZero
    }

    override fun part2(): Int {
        TODO()
    }
}
