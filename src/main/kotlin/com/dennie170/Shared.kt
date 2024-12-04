package com.dennie170

import kotlin.time.Duration
import kotlin.time.measureTime

const val reset = "\u001b[0m"

const val yellow = "\u001b[33m"
const val green = "\u001b[92m"
const val blue = "\u001b[94m"

fun <T> runDayMeasured(day: Day<T>): Duration {
    println("${yellow}Running Day ${day.day}:$reset")

    val setupTime = measureTime {
        day.setUp()
    }

    println("  ${yellow}Setup [$blue${setupTime}$reset]")

    var part1Result: T
    var part2Result: T

    val part1Time = try {
        measureTime { part1Result = day.part1() }
    } catch (e: NotImplementedError) {
        @Suppress("UNCHECKED_CAST")
        part1Result = (-1 as T)
        Duration.ZERO
    }

    val part2Time = try {
        measureTime { part2Result = day.part2() }
    } catch (e: NotImplementedError) {
        @Suppress("UNCHECKED_CAST")
        part2Result = (-1 as T)
        Duration.ZERO
    }

    val totalTime = part1Time + part2Time

    println("    ${yellow}Solution for part 1: $green$part1Result$reset. [$blue${part1Time}$reset]")
    println("    ${yellow}Solution for part 2: $green$part2Result$reset. [$blue${part2Time}$reset]")
    println("${yellow}Day ${day.day} total: [$blue$totalTime$reset]")
    println()

    return totalTime
}
