package com.dennie170.challenges.year2024

import com.dennie170.Day

class Day14 : Day<Int>(2024, 14) {

    companion object {
        const val GRID_WIDTH = 101
        const val GRID_HEIGHT = 103

        const val SECONDS_TO_RUN = 100
    }

    private lateinit var lines: List<String>

    private data class Position(var x: Int, var y: Int)
    private data class Velocity(val x: Int, val y: Int)
    private data class Robot(val position: Position, val velocity: Velocity) {

        fun moveTick() {
            val (x, y) = velocity

            if (position.x + x > GRID_WIDTH - 1) {
                position.x = position.x + x - GRID_WIDTH
            } else if (position.x + x < 0) {
                position.x += x + (GRID_WIDTH)

            } else {
                position.x += x
            }

            if (position.y + y > GRID_HEIGHT - 1) {
                position.y = position.y + y - GRID_HEIGHT
            } else if (position.y + y < 0) {

                position.y += y + (GRID_HEIGHT)
            } else {
                position.y += y
            }

        }
    }


    override fun setUp() {
        lines = super.readInput().lines()
    }

    private fun parseInstruction(line: String): Robot {
        val split = line.split(' ')
        val coordinates = split[0].substringAfter('=').split(',').map { it.toInt() }.let { Position(it[0], it[1]) }
        val velocity = split[1].substringAfter('=').split(',').map { it.toInt() }.let { Velocity(it[0], it[1]) }

        return Robot(coordinates, velocity)
    }

    private fun draw(robots: List<Robot>) {
        val matrix = Array(GRID_HEIGHT) { CharArray(GRID_WIDTH) { '.' } }

        for (row in matrix.indices) {
            for (col in matrix[row].indices) {
                var found = 0

                for (robot in robots) {
                    if (robot.position.x == col && robot.position.y == row) {
                        found++
                    }
                }
                if (found > 0) {
                    print(found)
                } else print('.')
            }
            println()
        }
    }

    override fun part1(): Int {
        val robots = lines.map { parseInstruction(it) }

        for (tick in 0..<SECONDS_TO_RUN) {

            for (robot in robots) {
                robot.moveTick()
            }

        }

        val quadrants = mutableListOf<List<Robot>>()


        quadrants.add(
            robots.filter {
                (0..<((GRID_WIDTH - 1) / 2)).contains(it.position.x) && (0..<((GRID_HEIGHT - 1) / 2)).contains(it.position.y)
            }
        )


        quadrants.add(
            robots.filter {
                (((GRID_WIDTH - 1) / 2) + 1..GRID_WIDTH).contains(it.position.x) && (0..<((GRID_HEIGHT - 1) / 2)).contains(it.position.y)
            }
        )


        quadrants.add(
            robots.filter {
                (0..<((GRID_WIDTH - 1) / 2)).contains(it.position.x) && (((GRID_HEIGHT - 1) / 2) + 1..GRID_HEIGHT).contains(it.position.y)
            }
        )

        quadrants.add(
            robots.filter {
                (((GRID_WIDTH - 1) / 2) + 1..GRID_WIDTH).contains(it.position.x) && (((GRID_HEIGHT - 1) / 2) + 1..GRID_HEIGHT).contains(it.position.y)
            }
        )


        return quadrants.fold(1) { acc, it ->
            if (it.isEmpty()) acc else acc * it.size
        }
    }

    override fun part2(): Int {
        TODO()
    }
}
