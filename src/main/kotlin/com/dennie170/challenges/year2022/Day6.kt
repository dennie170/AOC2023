package com.dennie170.challenges.year2022

import com.dennie170.Day

class Day6 : Day<Int>(2022, 6) {

    private lateinit var input: List<Char>


    override fun setUp() {
        input = super.readInput().toCharArray().toList()
    }

    override fun part1(): Int {

        for((counter, window) in input.windowed(4).withIndex()) {
            if(window.groupingBy { char -> char }.eachCount().size == 4) return counter + 4
        }

       return -1
    }


    override fun part2(): Int {
       return -1
    }
}
