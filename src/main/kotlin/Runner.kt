import com.dennie170.Day
import kotlin.time.Duration

fun main() {
//   runAll()

    runDay(4)
}

fun runAll() {
    var totalDuration = Duration.ZERO

    for (i in 1..31) {
        try {
            totalDuration += runDay(i)
        } catch (_: ClassNotFoundException) {
        }
    }

    println("${yellow}TOTAL TIME TAKEN: $blue$totalDuration")
}

fun runDay(day: Int): Duration {
    val clazz = Any::class::class.java.classLoader.loadClass("com.dennie170.challenges.Day$day")
    val dayInstance = clazz.getConstructor().newInstance() as Day<*>

    dayInstance.setUp()

    return runDayMeasured(dayInstance)
}
