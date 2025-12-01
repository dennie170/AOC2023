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

    var currentPosition = 50

    var timesOnZero = 0

    override fun part1(): Int {
        val instructions = lines.map {
            val direction = if (it.first() == 'L') Direction.LEFT else Direction.RIGHT
            val amount = it.substring(1).toInt()
            Instruction(direction, amount)
        }

        for (instruction in instructions) {
            var next = currentPosition

            val amount = if (instruction.amount / 100 > 0) {
                instruction.amount % 100
            } else instruction.amount

            next = if (instruction.direction == Direction.LEFT) {
                next - amount
            } else {
                next + amount
            }

            // Clamp within the amount of the circle
            if (next > 99) {
                next = next - 100
            }

            if (next < 0) {
                next = 100 - abs(next)
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
