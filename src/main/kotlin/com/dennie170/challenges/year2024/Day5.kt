package com.dennie170.challenges.year2024

import com.dennie170.Day

class Day5 : Day<Int>(2024, 5) {

    private val input: String = readInput()

    override fun part1(): Int {
        val (ruleInput, pageProductionInput) = input.split("\n\n")

        val rules = getRules(ruleInput)

        val pageProductions = getPageProductions(pageProductionInput)

        return pageProductions.filter { isValidSequence(rules, it).result }.sumOf {
            it[(it.size / 2)]
        }
    }

    override fun part2(): Int {
        val (ruleInput, pageProductionInput) = input.split("\n\n")

        val rules = getRules(ruleInput)

        return getPageProductions(pageProductionInput)
            .mapNotNull {
                val result = isValidSequence(rules, it)

                if (result.result) return@mapNotNull null

                val reordered = it.toMutableList()

                var isValid = result

                while (!isValid.result) {
                    val failedFirstIndex = reordered.indexOf(isValid.failedPair!!.first)
                    val failedSecondIndex = reordered.indexOf(isValid.failedPair!!.second)

                    reordered[failedFirstIndex] = isValid.failedPair!!.second
                    reordered[failedSecondIndex] = isValid.failedPair!!.first

                    isValid = isValidSequence(rules, reordered)
                }

                reordered
            }.sumOf {
                it[it.size / 2]
            }
    }

    private fun getPageProductions(pageProductionInput: String): List<List<Int>> {
        return pageProductionInput.split('\n').map {
            it.split(',').map { page -> page.toInt() }
        }
    }

    private fun getRules(ruleInput: String): Set<Pair<Int, Int>> {
        return ruleInput.split('\n').map {
            val split = it.split('|').map { page -> page.toInt() }
            split[0] to split[1]
        }.toSet()
    }

    private data class IsValidResponse(val result: Boolean, val failedPair: Pair<Int, Int>?)

    private fun isValidSequence(rules: Set<Pair<Int, Int>>, list: List<Int>): IsValidResponse {
        for (window in list.windowed(2)) {
            if (!rules.contains(Pair(window[0], window[1]))) {
                return IsValidResponse(false, Pair(window[0], window[1]))
            }
        }

        return IsValidResponse(true, null)
    }
}
