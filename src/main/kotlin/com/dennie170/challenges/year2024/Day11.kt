package com.dennie170.challenges.year2024

import com.dennie170.Day
import com.dennie170.common.splitInHalf

class Day11 : Day<Long>(2024, 11) {

    lateinit var input: LongArray

    override fun setUp() {
        input = super.readInput().split(' ').map { it.toLong() }.toLongArray()
    }

    private var stones = mutableMapOf<Long, Long>()

    private fun blink() {
        val newStonesMap = mutableMapOf<Long, Long>()
        for ((stone, count) in stones) {
            if (stone == 0L) {
                if (newStonesMap.contains(1L)) {
                    newStonesMap[1L] = newStonesMap[1L]!! + count
                } else {
                    newStonesMap[1L] = count
                }

                continue
            }

            val stoneString = stone.toString()

            if (stoneString.length % 2 == 0) {
                // split

                val (firstHalf, secondHalf) = stoneString.splitInHalf()

                val first = firstHalf.toLong()
                val second = secondHalf.toLong()

                if (newStonesMap.contains(first)) {
                    newStonesMap[first] = newStonesMap[first]!! + count
                } else newStonesMap[first] = count

                if (newStonesMap.contains(second)) {
                    newStonesMap[second] = newStonesMap[second]!! + count
                } else newStonesMap[second] = count

                continue
            }

            newStonesMap[stone * 2024] = count
        }

        stones = newStonesMap
    }

    override fun part1(): Long {

        for (stone in input) {
            stones[stone] = 1L
        }

        for (i in 0..<25) {
            blink()
        }

        return stones.toList().sumOf { it.second }
    }

    override fun part2(): Long {
        TODO()
    }
}
