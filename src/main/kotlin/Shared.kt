import kotlin.time.measureTime

inline fun <T>runMeasuredSolution(part: Int, block: () -> T) {
    val reset = "\u001b[0m"

    val yellow = "\u001b[33m"
    val green = "\u001b[92m"
    val blue = "\u001b[94m"

    val result: T
    val resultTime = measureTime {
        result = block()
    }

    println("${yellow}Solution for part $part: $green$result$reset. [$blue${resultTime}$reset]")
}
