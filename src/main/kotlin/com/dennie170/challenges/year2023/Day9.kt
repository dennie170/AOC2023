package com.dennie170.challenges.year2023

import com.dennie170.Day


class Day9 : Day<Int>(2023, 9) {

    private lateinit var input: List<String>

    override fun setUp() {
        input = super.readInput().lines()
    }

    private fun getExtrapolatedList(input: List<Int>): List<List<Int>> {
        val extrapolated = mutableListOf(input)

        var currentList = input

        while(true) {
            currentList = getDifferencesPerList(currentList)
            extrapolated.add(currentList)

            // Keep extrapolating till we have a list of only zeroes
            if(currentList.sum() == 0) break
        }

        return extrapolated
    }

    /**
     * IN:  [0, 3, 6, 9, 12, 15]
     * OUT:   [3, 3, 3, 3, 3] Always 1 less
     */
    private fun getDifferencesPerList(input: List<Int>): List<Int> {
        val ret = mutableListOf<Int>()

        for (i in input.indices) {
            if(i + 1 == input.size) break

            ret.add(input[i+1] - input[i])
        }

        return ret
    }

    /**
     * Works for 1 sequence?
     */
    private fun extrapolate(input:  List<List<Int>>): Int {
        var valueToIncreaseWith = 0

        // Walk over the list backwards, starting at [0,0,0...]
        for (list in input.reversed()) {
            valueToIncreaseWith += list.last()
            list.addLast(valueToIncreaseWith)
        }

        return valueToIncreaseWith
    }

    override fun part1(): Int {
        return input.map { line -> line.split(' ').map { char -> char.toInt()} }
            .map(::getExtrapolatedList)
            .map(::extrapolate)
            .sum()
    }

    override fun part2(): Int {
        TODO()
    }
}

// 1882395907 -> Good
// 1882395934 -> Too high



