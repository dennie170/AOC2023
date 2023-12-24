package com.dennie170.challenges.year2023

import com.dennie170.Day
import com.dennie170.common.Vector3d
import com.dennie170.common.getIntersectionPoint
import java.awt.geom.Area
import java.awt.geom.Line2D
import java.awt.geom.Rectangle2D
import kotlin.math.abs

class Day24 : Day<Long>(2023, 24) {

    private lateinit var input: Sequence<String>

    companion object {
        private val TEST_AREA = Area(
            Rectangle2D.Float(
                2.00000001E14f, // Float rep of 200_000_000_000_000
                2.00000001E14f, // Float rep of 200_000_000_000_000
                2.00000001E14f, // Float rep of 200_000_000_000_000
                2.00000001E14f, // Float rep of 200_000_000_000_000
            )
        )
    }

    override fun setUp() {
        input = super.readInput().lineSequence()
    }

    override fun part1(): Long {
        val stones = input.map(::parseHailstone)

        val maxTime = stones.maxOf(::getNanosecondsToFlyOutOfTestArea)

        val lines = stones.map { it.toLine2D(maxTime) }

        val intersections = mutableSetOf<Pair<Int, Int>>()

        var collisions = 0L

        for ((index, line) in lines.withIndex()) {

            for ((i, l) in lines.withIndex()) {
                if (i == index) continue
                if (line.intersectsLine(l)) {
                    val point = getIntersectionPoint(line, l) ?: continue

                    if (TEST_AREA.contains(point) && (!intersections.contains(index to i) && !intersections.contains(i to index))) {
                        collisions++
                        intersections.add(index to i)
                    }

                }
            }

        }

        return collisions
    }

    private fun getNanosecondsToFlyOutOfTestArea(hailstone: Hailstone): Long {
        return maxOf(
            abs((TEST_AREA.bounds.x - hailstone.location.x) / hailstone.velocity.x),
            abs((TEST_AREA.bounds.y - hailstone.location.y) / hailstone.velocity.y),
        )
    }

    private fun parseHailstone(line: String): Hailstone {
        return line.split('@')
            .map { it.split(", ").map(String::trim).map(String::toLong) }
            .map { x -> Vector3d(x[0], x[1], x[2]) }
            .let { (location, velocity) -> Hailstone(location, velocity) }
    }

    private data class Hailstone(val location: Vector3d<Long>, val velocity: Vector3d<Long>) {
        fun toLine2D(time: Long): Line2D {
            val endX = location.x + (velocity.x * time)
            val endY = location.y + (velocity.y * time)

            return Line2D.Float(location.x.toFloat(), location.y.toFloat(), endX.toFloat(), endY.toFloat())
        }
    }

    override fun part2(): Long {
        TODO("Not yet implemented")
    }
}
