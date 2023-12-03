package com.dennie170

abstract class Day<T>(val day: Int) {

    fun setUp() {}

    abstract fun part1(): T

    abstract fun part2(): T
}
