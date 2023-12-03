import com.dennie170.Day
import kotlin.time.Duration
import kotlin.time.measureTime

const val reset = "\u001b[0m"

const val yellow = "\u001b[33m"
const val green = "\u001b[92m"
const val blue = "\u001b[94m"

fun <T> runDayMeasured(day: Day<T>): Duration {
    println("${yellow}Running Day ${day.day}:$reset")
    var part1Result: T
    var part2Result: T

    val part1Time = measureTime { part1Result = day.part1() }
    val part2Time = measureTime { part2Result = day.part2() }
    val totalTime = part1Time + part2Time

    println("    ${yellow}Solution for part 1: $green$part1Result$reset. [$blue${part1Time}$reset]")
    println("    ${yellow}Solution for part 2: $green$part2Result$reset. [$blue${part2Time}$reset]")
    println("${yellow}Day ${day.day} total: [$blue$totalTime$reset]")
    println()

    return totalTime
}
