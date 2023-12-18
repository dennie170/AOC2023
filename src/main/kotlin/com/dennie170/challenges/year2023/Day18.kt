package com.dennie170.challenges.year2023

import com.dennie170.Day
import java.awt.Polygon


class Day18 : Day<Int>(2023, 18) {

    private lateinit var input: Sequence<String>
    private lateinit var matrix: Array<Array<Colour>>


    override fun setUp() {
        input = super.readInput().lineSequence()

    }

    override fun part1(): Int {
        val polygon = Polygon()

        var y = 0
        var x = 0

        for (instruction in input.map(Instruction::parse)) {
            when (instruction.direction) {
                Direction.UP -> {
                    for (i in 0..<instruction.steps) {
                        polygon.addPoint(y, x)
                        y++
                    }
                }

                Direction.RIGHT -> {
                    for (i in 0..<instruction.steps) {
                        polygon.addPoint(y, x)
                        x++
                    }
                }

                Direction.DOWN -> {
                    for (i in 0..<instruction.steps) {
                        polygon.addPoint(y, x)
                        y--
                    }
                }

                Direction.LEFT -> {
                    for (i in 0..<instruction.steps) {
                        polygon.addPoint(y, x)
                        x--
                    }
                }
            }
        }


        return getPolygonSurface(polygon)
    }

    private fun getPolygonSurface(polygon: Polygon): Int {
        var total = 0

        val x_min = polygon.xpoints.min() - 1
        val x_max = polygon.xpoints.max() + 1

        val y_min = polygon.ypoints.min() - 1
        val y_max = polygon.ypoints.max() + 1

        val zipped = polygon.xpoints.zip(polygon.ypoints).toSet()

        for (x in x_min..x_max) {
            for (y in y_min..y_max) {
                if (polygon.contains(x, y) || zipped.contains(Pair(x, y))) {
                    total++
                }
            }
        }

        return total
    }


    override fun part2(): Int {
        return -1
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
}
