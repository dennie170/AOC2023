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
                newStonesMap[1L] = newStonesMap.getOrDefault(1L, 0L) + count
                continue
            }

            val stoneString = stone.toString()

            if (stoneString.length % 2 == 0) {
                // split

                val (firstHalf, secondHalf) = stoneString.splitInHalf()

                val first = firstHalf.toLong()
                val second = secondHalf.toLong()

                newStonesMap[first] = newStonesMap.getOrDefault(first, 0L) + count
                newStonesMap[second] = newStonesMap.getOrDefault(second, 0L) + count

                continue
            }

            newStonesMap[stone * 2024] = count
        }

        stones = newStonesMap
    }

    override fun part1(): Long {
        return solve(25)
    }

    private fun solve(blinks: Int): Long {
        stones.clear()

        for (stone in input) {
            stones[stone] = 1L
        }

        for (i in 0..<blinks) {
            blink()
        }

        return stones.toList().sumOf { it.second }
    }

    override fun part2(): Long {
        return solve(75)
    }
}
