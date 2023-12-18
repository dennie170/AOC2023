package com.dennie170.challenges.year2023

import com.dennie170.Day
import java.awt.Polygon


class Day18 : Day<Int>(2023, 18) {

    private lateinit var input: Sequence<String>

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
                    polygon.addPoint(y, x)
                    y += instruction.steps
                }

                Direction.RIGHT -> {
                    polygon.addPoint(y, x)
                    x += instruction.steps
                }

                Direction.DOWN -> {
                    polygon.addPoint(y, x)
                    y -= instruction.steps
                }

                Direction.LEFT -> {
                    polygon.addPoint(y, x)
                    x -= instruction.steps
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

        val isWall: (x: Int, y: Int) -> Boolean = { x, y ->
            zipped.windowed(2).any {
                val xrange = if (it[0].first < it[1].first) it[0].first..it[1].first else it[0].first downTo it[1].first
                val yrange = if (it[0].second < it[1].second) it[0].second..it[1].second else it[0].second downTo it[1].second

                xrange.contains(x) && yrange.contains(y)
            }
        }

        for (x in x_min..x_max) {
            for (y in y_min..y_max) {
                if (polygon.contains(x, y)) {
                    total++
                } else {
                    if (isWall(x, y)) {
                        total++
                    }
                }
            }
        }

        return total
    }


    override fun part2(): Int {
        return -1
        val polygon = Polygon()

        var y = 0
        var x = 0

        for (instruction in input.map(Instruction::parsePart2)) {
            when (instruction.direction) {
                Direction.UP -> {
                    polygon.addPoint(y, x)
                    y += instruction.steps
                }

                Direction.RIGHT -> {
                    polygon.addPoint(y, x)
                    x += instruction.steps
                }

                Direction.DOWN -> {
                    polygon.addPoint(y, x)
                    y -= instruction.steps
                }

                Direction.LEFT -> {
                    polygon.addPoint(y, x)
                    x -= instruction.steps
                }
            }
        }

        return getPolygonSurface(polygon)
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
}
