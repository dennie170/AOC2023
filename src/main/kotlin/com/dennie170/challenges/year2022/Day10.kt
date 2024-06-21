package com.dennie170.challenges.year2022

import com.dennie170.Day


class Day10 : Day<Int>(2022, 9) {

    private lateinit var input: String

    override fun setUp() {
        input = readInput()
    }

    enum class OpType {
        NOOP,
        ADDX,
    }

    data class Operation(val type: OpType, val value: Int?)

    private fun parseInstructions(input: String): List<Operation> {
        return input.lineSequence().map {
            if (it.substring(0, 4) == "noop") Operation(OpType.NOOP, null) else Operation(OpType.ADDX, it.substring(5).toInt())
        }.toList()
    }

    override fun part1(): Int {
        val instructions = parseInstructions(input)

        var cycle = 0
        var operation = 0
        var currentOp: Operation? = null
        var x = 1

        val markers = arrayOf(20, 60, 100, 140, 180, 220)

        var summed = 0

        do {
            cycle++

            if (markers.contains(cycle)) {
                summed += cycle * x
            }

            if (currentOp == null) {
                val op = instructions[operation++]

                if (op.type == OpType.NOOP) continue

                if (op.type == OpType.ADDX) {
                    currentOp = op
                }
            } else {
                x += currentOp.value!!
                currentOp = null
            }

        } while (operation < instructions.size)

        return summed
    }

    // RFKZCPEF
    override fun part2(): Int {
        val instructions = parseInstructions(input)

        var cycle = 0
        var operation = 0
        var currentOp: Operation? = null
        var x = 1

        var crtPosX = 0
        var crtPosY = 0

        do {
            cycle++

            // Draw CRT pixel
            if (crtPosX in x - 1..x + 1) {
                print('#')
            } else {
                print('.')
            }

            if (crtPosX < 39) {
                crtPosX++
            } else {
                crtPosX = 0
                crtPosY++
                println() // Draw new line
            }


            // Handle instructoins
            if (currentOp == null) {
                val op = instructions[operation++]

                if (op.type == OpType.NOOP) continue

                if (op.type == OpType.ADDX) {
                    currentOp = op
                }
            } else {
                x += currentOp.value!!
                currentOp = null
            }

        } while (operation < instructions.size)

        return 0
    }


}
