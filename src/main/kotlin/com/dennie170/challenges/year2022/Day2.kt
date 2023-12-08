package com.dennie170.challenges.year2022

import com.dennie170.Day
import com.dennie170.common.StringSequence

class Day2 : Day<Int>(2022, 2) {

    private lateinit var input: StringSequence

    override fun setUp() {
        input = super.readInput().lineSequence()
    }

    override fun part1(): Int {
        return input.map {
            val arr = it.split(' ').map { c -> c.single().code }.toIntArray()

            val opp = when (arr[0]) {
                65 -> Hand.ROCK
                66 -> Hand.PAPER
                67 -> Hand.SCISSORS
                else -> throw RuntimeException()
            }

            val me = when (arr[1]) {
                88 -> Hand.ROCK
                89 -> Hand.PAPER
                90 -> Hand.SCISSORS
                else -> throw RuntimeException()
            }

            OneVersusOneMatch(opp, me)
        }.map {
            when (it.opp) {
                Hand.ROCK -> when (it.me) {
                    Hand.PAPER -> Result.WIN
                    Hand.ROCK -> Result.DRAW
                    Hand.SCISSORS -> Result.LOSE
                }

                Hand.PAPER -> when (it.me) {
                    Hand.PAPER -> Result.DRAW
                    Hand.ROCK -> Result.LOSE
                    Hand.SCISSORS -> Result.WIN
                }

                Hand.SCISSORS -> when (it.me) {
                    Hand.PAPER -> Result.LOSE
                    Hand.ROCK -> Result.WIN
                    Hand.SCISSORS -> Result.DRAW
                }
            }.score + it.me.value
        }.reduce { acc, i -> acc + i }
    }

    override fun part2(): Int {
        return input.map {
            val arr = it.split(' ').map { char ->
                char.single().code
            }.toIntArray()

            MatchWithDesiredResult(arr[0], Result.fromInt(arr[1]))
        }.map { match ->
            val outcomeScore = match.desiredResult.score

            val playedCard = getPlayedCardScore(match.opp, match.desiredResult)

            playedCard + outcomeScore
        }.reduce { acc, i -> acc + i }
    }

    private fun getPlayedCardScore(opp: Int, desiredResult: Result): Int {
        return when (opp) {
            65 -> when (desiredResult) { // ROCK
                Result.LOSE -> Hand.SCISSORS // Scissors
                Result.DRAW -> Hand.ROCK // Rock
                Result.WIN -> Hand.PAPER // Paper
            }

            66 -> when (desiredResult) { // PAPER
                Result.LOSE -> Hand.ROCK // Rock
                Result.DRAW -> Hand.PAPER // Paper
                Result.WIN -> Hand.SCISSORS // Scissors
            }

            67 -> when (desiredResult) { // SCISSORS
                Result.LOSE -> Hand.PAPER // Paper
                Result.DRAW -> Hand.SCISSORS // Scissors
                Result.WIN -> Hand.ROCK // Rock
            }

            else -> throw RuntimeException()
        }.value
    }

    data class MatchWithDesiredResult(val opp: Int, val desiredResult: Result)
    data class OneVersusOneMatch(val opp: Hand, val me: Hand)

    enum class Hand(val value: Int) {
        ROCK(1),
        PAPER(2),
        SCISSORS(3);
    }

    enum class Result(val score: Int) {
        LOSE(0),
        DRAW(3),
        WIN(6);

        companion object {
            fun fromInt(input: Int): Result {
                return when (input) {
                    90 -> WIN // Z
                    89 -> DRAW // Y
                    88 -> LOSE // X
                    else -> throw RuntimeException()
                }
            }
        }
    }

}
