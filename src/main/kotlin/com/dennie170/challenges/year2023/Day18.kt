package com.dennie170.challenges.year2023

import com.dennie170.Day
import kotlin.math.abs


class Day18 : Day<Long>(2023, 18) {

    private lateinit var input: List<String>

    override fun setUp() {
        input = super.readInput().lines()
    }

    private var totalLength = 1L

    private fun solve(instructions: List<Instruction>): Long {
        val list = mutableListOf<Coordinate>()

        var y = 0L
        var x = 0L

        for (instruction in instructions) {
            totalLength += instruction.steps

            list.add(Coordinate(x, y))

            when (instruction.direction) {
                Direction.UP -> {
                    y += instruction.steps
                }

                Direction.RIGHT -> {
                    x += instruction.steps
                }

                Direction.DOWN -> {
                    y -= instruction.steps
                }

                Direction.LEFT -> {
                    x -= instruction.steps
                }
            }
        }

        list.add(Coordinate(0, 0))

        return getPolygonSurface(list)
    }


    private fun getPolygonSurface(polygon: List<Coordinate>): Long {
        val area = abs(polygon.windowed(2).sumOf {
            (it[0].x * it[1].y) - (it[0].y * it[1].x) // Magic 😎
        }) / 2

        return area + (totalLength / 2) + 1
    }

    override fun part1(): Long {
        totalLength = 1L
        return solve(input.map(Instruction::parse))
    }

    override fun part2(): Long {
        totalLength = 1L
        return solve(input.map(Instruction::parsePart2))
    }


    private data class Instruction(val direction: Direction, val steps: Int, val colour: Colour) {
        companion object {
            fun parse(line: String): Instruction {
                val dir = when (line.first()) {
                    'U' -> Direction.UP
                    'R' -> Direction.RIGHT
                    'D' -> Direction.DOWN
                    'L' -> Direction.LEFT
                    else -> throw IllegalStateException()
                }

                val s = line.substringBefore('(').substring(1).trim().toInt()
                val col = line.substringAfter('#').substringBefore(')')

                return Instruction(dir, s, Colour(col))
            }

            fun parsePart2(line: String): Instruction {
                val hex = line.substringAfter('#').substringBefore(')')

                val direction = when (hex.last()) {
                    '3' -> Direction.UP
                    '0' -> Direction.RIGHT
                    '1' -> Direction.DOWN
                    '2' -> Direction.LEFT
                    else -> throw IllegalStateException()
                }

                val steps = Integer.decode("0x" + hex.substring(0, hex.length - 1))

                return Instruction(direction, steps, Colour(hex))
            }
        }
    }

    private enum class Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT
    }

    @JvmInline
    private value class Colour(val colour: String)

    private data class Coordinate(val x: Long, val y: Long)
}
