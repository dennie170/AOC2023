package com.dennie170.challenges.year2023

import com.dennie170.Day
import com.dennie170.common.getMatrix


class Day17 : Day<Int>(2023, 17) {

    private lateinit var input: String
    private lateinit var matrix: Array<IntArray>

    override fun setUp() {
        input = super.readInput()
        matrix = getMatrix(input.toCharArray().filter { it != '\n' }.map { it.digitToInt() })
    }

    override fun part1(): Int {

        return -1
    }


    override fun part2(): Int {
        return -1
    }


}
