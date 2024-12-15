package com.dennie170.challenges.year2024

import com.dennie170.Day
import java.util.Queue

class Day15 : Day<Int>(2024, 14) {

    enum class Direction{
        UP,
        DOWN,
        LEFT,
        RIGHT,
    }

    private lateinit var matrix: Array<CharArray>

    private lateinit var instructions: Queue<Direction>

    override fun setUp() {
//        lines = super.readInput().lines()
    }

    override fun part1(): Int {
        return -1
    }

    override fun part2(): Int {
        TODO()
    }
}
