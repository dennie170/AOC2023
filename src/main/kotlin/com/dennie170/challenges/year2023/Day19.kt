package com.dennie170.challenges.year2023

import com.dennie170.Day


class Day19 : Day<Long>(2023, 19) {

    private lateinit var input: String

    private lateinit var workflows: Map<String, Workflow>

    override fun setUp() {
        input = super.readInput()
    }

    private fun mapWorkflow(line: String): Workflow {
        val name = line.substringBefore('{')
        val fallback = line.substringAfterLast(',').trim('}')
        val rules = line.substringAfter('{').substringBeforeLast(',').split(',').map {
            val property = it[0]
            val operator = it[1]
            val value = it.substringAfter(operator).substringBefore(':').trim().toInt()
            val destination = it.substringAfter(':')

            Rule(property, operator, value, destination)
        }

        return Workflow(name, rules, fallback)
    }

    private fun mapRating(line: String): Map<Char, Int> {
        return line.substring(1, line.length - 1).split(',').associate {
            val split = it.split('=')
            split[0].first() to split[1].toInt()
        }
    }

    private tailrec fun runRatingInWorkflow(rating: Map<Char, Int>, workflow: Workflow): Boolean {
        for (rule in workflow.rules) {
            if (rule.operator == '>') {
                if (rating[rule.property]!! > rule.value) {
                    return when (rule.destination) {
                        "A" -> true
                        "R" -> false
                        else -> runRatingInWorkflow(rating, workflows[rule.destination]!!)
                    }
                }
            } else {
                if (rating[rule.property]!! < rule.value) {
                    return when (rule.destination) {
                        "A" -> true
                        "R" -> false
                        else -> runRatingInWorkflow(rating, workflows[rule.destination]!!)
                    }
                }
            }
        }

        return when (workflow.fallback) {
            "A" -> true
            "R" -> false
            else -> runRatingInWorkflow(rating, workflows[workflow.fallback]!!)
        }
    }


    override fun part1(): Long {
        val (w, r) = input.split("\n\n")
        workflows = w.lines().map(::mapWorkflow).associateBy { it.name }
        val ratings = r.lines().map(::mapRating)

        val inFlow = workflows["in"]!!

        return ratings.filter { runRatingInWorkflow(it, inFlow) }.sumOf {
            it.values.sum().toLong()
        }
    }

    override fun part2(): Long {
        val (w, r) = input.split("\n\n")
        workflows = w.lines().map(::mapWorkflow).associateBy { it.name }

        val rating = mutableMapOf(
            'x' to 1..4000,
            'm' to 1..4000,
            'a' to 1..4000,
            's' to 1..4000,
        )

        return runRangeInto(rating.toMutableMap(), "in")
    }

    private fun runRangeInto(rating: MutableMap<Char, IntRange>, into: String): Long {
        if (into == "R") return 0
        if (into == "A") return getPossibilities(rating.toMutableMap())

        val workflow = workflows[into]!!

        var possibilities = 0L


        for (rule in workflow.rules) {
            val range = rating[rule.property]!!

            if (rule.operator == '>') {
                if (range.first > rule.value) return possibilities + runRangeInto(rating.toMutableMap(), rule.destination)

                if (range.last > rule.value) {
                    val newRating = rating.toMutableMap()
                    newRating[rule.property] = rule.value + 1..range.last
                    possibilities += runRangeInto(newRating.toMutableMap(), rule.destination)

                    rating[rule.property] = range.first..rule.value
                }
            } else {
                if (range.last < rule.value) return possibilities + runRangeInto(rating.toMutableMap(), rule.destination)

                if (range.first < rule.value) {
                    val newRating = rating.toMutableMap()
                    newRating[rule.property] = range.first..<rule.value
                    possibilities += runRangeInto(newRating.toMutableMap(), rule.destination)

                    rating[rule.property] = range.first..rule.value
                }
            }

        }

        return possibilities
    }

    private fun getPossibilities(rating: Map<Char, IntRange>): Long {
        return rating.values.fold(1L) { acc, range ->
            acc * (range.last - range.first + 1)
        }
    }

    private data class Workflow(val name: String, val rules: List<Rule>, val fallback: String)
    private data class Rule(val property: Char, val operator: Char, val value: Int, val destination: String)
}
