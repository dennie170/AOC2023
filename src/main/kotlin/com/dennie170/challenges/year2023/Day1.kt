package com.dennie170.challenges.year2023

import com.dennie170.Day

class Day1 : Day<Int>(2023, 1) {
    private lateinit var input: Sequence<String>

    override fun setUp() {
        input = super.readInput().lineSequence()
    }

    override fun part1(): Int {
        return input.map {
            val res = it.replace(Regex("\\D"), "")

            if (res.length == 1) res.repeat(2) else res
        }.map {
            "${it.first()}${it.last()}".toInt()
        }.reduce { acc, item ->
            acc + item
        }
    }

    override fun part2(): Int {
        return input.map {
            it.replace("twone", "21")
                .replace("oneight", "18")
                .replace("eightwo", "82")
                .replace("eighthree", "83")
                .replace("nineight", "98")
                .replace("sevenine", "79")
                .replace("fiveight", "58")
                .replace("one", "1")
                .replace("two", "2")
                .replace("three", "3")
                .replace("four", "4")
                .replace("five", "5")
                .replace("six", "6")
                .replace("seven", "7")
                .replace("eight", "8")
                .replace("nine", "9")
                .replace(Regex("\\D"), "")
        }.map {
            if (it.length == 1) it.repeat(2) else it
        }.map {
            "${it.first()}${it.last()}".toInt()
        }.reduce { acc, item ->
            acc + item
        }
    }
}
