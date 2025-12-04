package com.dennie170

abstract class Day<out T>(private val year: Int, val day: Int) {
    protected fun readInput(): String {
        return this.javaClass.getResource("/input/$year/$day.txt")?.readText()?.trim('\n') ?: throw NoInputFileFoundException(year, day)
    }

    open fun setUp() {}

    abstract fun part1(): T

    abstract fun part2(): T
}

class NoInputFileFoundException(year: Int, day: Int) : RuntimeException("No Input file found for year $year day $day")
