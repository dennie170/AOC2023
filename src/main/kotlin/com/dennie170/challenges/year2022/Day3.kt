package com.dennie170.challenges.year2022

import com.dennie170.Day
import com.dennie170.common.StringSequence
import com.dennie170.common.intersectAll
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

            charScore(shared[0])
        }.sum()
    }

    private fun charScore(char: Char) = char.code - if (char.code >= 97) 96 else 38

    override fun part2(): Int {
        return input.chunked(3).map { team ->
            val rucksacks = team.map { it.toCharArray().toSet() }

            val shared = rucksacks.intersectAll().toList()

            if (shared.size != 1) throw RuntimeException()

            charScore(shared[0])
        }.sum()
    }
}
