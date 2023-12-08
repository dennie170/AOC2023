package com.dennie170.challenges.year2022

import com.dennie170.Day
import com.dennie170.common.StringSequence
import com.dennie170.common.splitInHalf

class Day3 : Day<Int>(2022, 3) {

    private lateinit var input: StringSequence

    override fun setUp() {
        input = super.readInput().lineSequence()
    }

    override fun part1(): Int {
        return input.map { it.splitInHalf() }.map { (one, two) ->
            val shared = one.toCharArray().intersect(two.toCharArray().toSet()).toList()

            if (shared.size != 1) throw RuntimeException()

            shared[0]
        }.map { char ->
            char.code - if(char.code >= 97) 96 else 38
        }.sum()
    }

    override fun part2(): Int {
        TODO("Not yet implemented")
    }
}
