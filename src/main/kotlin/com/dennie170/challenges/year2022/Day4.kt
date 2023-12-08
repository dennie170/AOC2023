package com.dennie170.challenges.year2022

import com.dennie170.Day
import com.dennie170.common.StringSequence
import com.dennie170.common.areCompletelyOverlapping

class Day4 : Day<Int>(2022, 4) {

    private lateinit var input: StringSequence

    override fun setUp() {
        input = super.readInput().lineSequence()
    }

    override fun part1(): Int {
        return input.map { pair ->
            pair.split(',').map { range ->
                val assignment = range.split('-').map(String::toInt)
                assignment[0].rangeTo(assignment[1])
            }
        }.count { (one, two) -> IntRange.areCompletelyOverlapping(one, two) }
    }


    override fun part2(): Int {
        return 1
    }
}
