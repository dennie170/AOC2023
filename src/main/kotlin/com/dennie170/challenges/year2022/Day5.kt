package com.dennie170.challenges.year2022

import com.dennie170.Day

class Day5 : Day<String>(2022, 5) {

    companion object {
        const val STACKS_COUNT = 9
        const val LINES_FOR_STACKS = 7

        val INSTRUCTION_REGEX = Regex("^move ([0-9]+) from (\\d) to (\\d)")
    }

    private lateinit var input: List<String>

    private val stacks = mutableMapOf<Int, MutableList<Char>>()

    override fun setUp() {
        input = super.readInput().lines()
    }

    override fun part1(): String {
        prepareStacks()

        val instructions = input.drop(LINES_FOR_STACKS + 2)

        for(instruction in instructions) {
            if(instruction.isEmpty()) continue
            val groups = INSTRUCTION_REGEX.matchEntire(instruction)!!.groups

            val count = groups[1]!!.value.toInt()
            val from = groups[2]!!.value.toInt()
            val to = groups[3]!!.value.toInt()

            for (i in 0..<count) {
                val intermediate = stacks[from]!!.removeFirst()

                stacks[to]!!.addFirst(intermediate)
            }
        }

        return String(stacks.values.map { it.first() }.toCharArray())
    }


    override fun part2(): String {
        prepareStacks()

        val instructions = input.drop(LINES_FOR_STACKS + 2)

        for(instruction in instructions) {
            if (instruction.isEmpty()) continue
            val groups = INSTRUCTION_REGEX.matchEntire(instruction)!!.groups

            val count = groups[1]!!.value.toInt()
            val from = groups[2]!!.value.toInt()
            val to = groups[3]!!.value.toInt()

            val intermediate = mutableListOf<Char>()

            for (i in 0..<count) {
                intermediate.addLast(stacks[from]!!.removeFirst())

            }

            for (i in intermediate.reversed()) {
                stacks[to]!!.addFirst(i)
            }
        }


        return String(stacks.values.map { it.first() }.toCharArray())
    }

    private fun prepareStacks() {
        for (i in 1..STACKS_COUNT) {
            stacks[i] = mutableListOf()
        }

        for (i in 0..LINES_FOR_STACKS) {
            var line = input[i].toCharArray().drop(1)

            for (j in 1..STACKS_COUNT) {
                if (line.isEmpty()) continue
                val char = line[0]

                if (char != ' ') {
                    stacks[j]!!.add(char)
                }

                line = line.drop(4)
            }
        }
    }
}
