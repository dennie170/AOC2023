package com.dennie170.challenges.year2023

import com.dennie170.Day

class Day6 : Day<Long>(2023, 6) {
    private lateinit var input: String

    override fun setUp() {
        input = super.readInput()
    }

    override fun part1(): Long {
        val lines = input.lines().map { it.substringAfter(':').trim() }
            .map { it.split(' ').map { number -> number.trim() }.filter { number -> number.isNotEmpty() }.map { number -> number.toLong() } }

        val races = lines[0].zip(lines[1]).map {
            Race(it.first, it.second)
        }

        return races.map(::getPossibilities)
            .fold(1) { acc, i ->
                acc * i
            }
    }

    private fun getPossibilities(race: Race): Long {
        var possibilities = 0L

        for(chargeTime in 0 .. race.time) {
            val distance = calculateDistance(chargeTime, race.time - chargeTime)

            if(distance > race.record) {
                possibilities++
            }
        }

        return possibilities
    }

    private fun calculateDistance(chargeTime: Long, remainingTime: Long): Long {
        return chargeTime * remainingTime
    }

    override fun part2(): Long {
        val race = input.lines().map { it.substringAfter(':').trim() }
            .map { it.replace(" ", "").trim().toLong() }
            .zipWithNext().map {
                Race(it.first, it.second)
            }.first()

        return getPossibilities(race)
    }

    data class Race(val time: Long, val record: Long)
}
