package com.dennie170.challenges.year2022

import com.dennie170.Day
import java.util.*


class Day11 : Day<Long>(2022, 11) {

    private lateinit var input: String

    private lateinit var monkeys: List<Monkey>

    private val monkeyInspections = mutableMapOf<Int, Int>()

    private data class Monkey(val items: Queue<Int>, val increaseWorryLevel: (Int) -> Int, val throwTo: (Int) -> Int)

    override fun setUp() {
        input = readInput()
    }

    private fun parseInput() {
        val sets = input.lineSequence().filter { it != "" }.chunked(6)

        monkeys = sets.map { set ->
            val items = LinkedList(set[1].substringAfter(": ").split(", ").map { it.toInt() })
            val operation = { old: Int ->
                val op = set[2].substringAfter("old ")

                val operator = op[0]
                val add = if (op.substring(2) == "old") old else op.substring(2).toInt()

                if (operator == '*') old * add else old + add
            }

            // Input is the worry level
            // Return is the monkey to throw towards to
            val throwTo = { value: Int ->
                val divisibleBy = set[3].substringAfter("by ").toInt()
                val ifTrue = set[4].last().digitToInt()
                val ifFalse = set[5].last().digitToInt()

                if (value % divisibleBy == 0) ifTrue else ifFalse
            }

            Monkey(items, operation, throwTo)
        }.toList()

        for ((index, monkey) in monkeys.withIndex()) {
            monkeyInspections[index] = 0
        }
    }

    override fun part1(): Long {
        parseInput()

        val rounds = 20

        for (round in 0..<rounds) {
            for ((index, monkey) in monkeys.withIndex()) {

                for (item in monkey.items.indices) {
                    if (monkeyInspections.contains(index)) {
                        monkeyInspections[index] = monkeyInspections[index]!!.inc()
                    }

                    // Inspect and get bored
                    val worryLevel = monkey.increaseWorryLevel(monkey.items.poll()) / 3

                    val throwTo = monkey.throwTo(worryLevel)

                    monkeys[throwTo].items.add(worryLevel)
                }

            }
        }

        return monkeyInspections.asSequence()
            .map { it.value }
            .sortedByDescending { it }
            .take(2)
            .fold(1L) { acc, i ->
                acc * i.toLong()
            }
    }


    override fun part2(): Long {
        TODO()
    }


}
