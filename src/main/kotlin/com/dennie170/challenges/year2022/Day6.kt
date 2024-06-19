package com.dennie170.challenges.year2022

import com.dennie170.Day

class Day6 : Day<Int>(2022, 6) {

    private lateinit var input: List<Char>


    override fun setUp() {
        input = super.readInput().toCharArray().toList()
    }

    // 1235
    override fun part1(): Int {

        for(index in 0..input.size) {
            val set = setOf(input[index], input[index + 1], input[index + 2], input[index + 3])

            if(set.size == 4) return index + 4
        }

       return -1
    }


    override fun part2(): Int {
       return -1
    }
}
