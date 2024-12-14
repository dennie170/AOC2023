package com.dennie170.challenges.year2024

import com.dennie170.Day
import com.dennie170.common.findLeastCommonMultiple
import com.dennie170.common.gcd

class Day13 : Day<Int>(2024, 13) {

    companion object {
        private const val A_COST = 3
        private const val B_COST = 1
    }

    lateinit var input: String

    override fun setUp() {
        input = super.readInput()
    }


    private data class Button(val cost: Int, val x: Int, val y: Int)
    private data class Prize(val x: Int, val y: Int)
    private data class Group(val buttonA: Button, val buttonB: Button, val prize: Prize)


    private fun parseGroup(str: String): Group {
        val lines = str.lines()

        val buttonRegex = Regex("X\\+([0-9]+), Y\\+([0-9]+)")

        val buttonA = buttonRegex.matchEntire(lines[0].substring(10))!!.groups.let { collection -> Button(3, collection[1]!!.value.toInt(), collection[2]!!.value.toInt()) }
        val buttonB = buttonRegex.matchEntire(lines[1].substring(10))!!.groups.let { collection -> Button(1, collection[1]!!.value.toInt(), collection[2]!!.value.toInt()) }

        val prize = Regex("X=(\\d+), Y=(\\d+)").matchEntire(lines[2].substring(7))!!.groups.let { collection -> Prize(collection[1]!!.value.toInt(), collection[2]!!.value.toInt()) }

        return Group(buttonA, buttonB, prize)
    }

    /// Returns total cost of winning the prize
    private fun execute(group: Group): Int {

        /*
        *
        Button A: X+94, Y+34
        Button B: X+22, Y+67
        Prize: X=8400, Y=5400
        */

        val prizeX = group.prize.x
        val prizeY = group.prize.y

//        val simplifiedX = (findLeastCommonMultiple(group.buttonB.x, group.buttonB.y) / group.buttonB.x) * 7870

        val leftFirst = group.buttonB.y * prizeX // Button B y * prize X
        val leftSecond = group.buttonB.y * -group.buttonA.x // Button B Y * -Button A X

        val rightFirst = group.buttonB.x * prizeY // Button B X * prizeY
        val rightSecond = group.buttonB.x * group.buttonA.y // Button B X *  Button A Y


        val second = leftSecond + rightSecond

        val buttonAPresses = (rightFirst - leftFirst) / second

        val buttonBPresses = rightSecond / group.buttonB.x

        return (buttonAPresses * 3) + buttonBPresses
    }


    override fun part1(): Int {
        return input.split("\n\n").map { parseGroup(it) }.sumOf {
            execute(it)
        }
    }

    override fun part2(): Int {
        TODO()
    }
}
