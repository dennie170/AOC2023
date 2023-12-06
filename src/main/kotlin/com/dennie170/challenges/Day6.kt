package com.dennie170.challenges

import com.dennie170.Day

class Day6 : Day<Int>(6) {
    private val input = """
        Time:        40     70     98     79
        Distance:   215   1051   2147   1005
    """.trimIndent()


    override fun part1(): Int {
        val lines = input.lines().map { it.substringAfter(':').trim() }
            .map { it.split(' ').map { number -> number.trim() }.filter { number -> number.isNotEmpty() }.map { number -> number.toInt() } }

        val races = lines[0].zip(lines[1]).map {
            Race(it.first, it.second)
        }

        return races.map(::getPossibilities)
            .fold(1) { acc, i ->
                acc * i
            }
    }

    private fun getPossibilities(race: Race): Int {
        var possibilities = 0

        for(chargeTime in 0 .. race.time) {
            val distance = calculateDistance(chargeTime, race.time - chargeTime)

            if(distance > race.record) {
                possibilities++
            }
        }

        return possibilities
    }

    private fun calculateDistance(chargeTime: Int, remainingTime: Int): Int {
        return chargeTime * remainingTime
    }

    override fun part2(): Int {
        return -1
    }

    data class Race(val time: Int, val record: Int)
}
