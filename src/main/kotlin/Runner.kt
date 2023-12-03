import com.dennie170.Day
import kotlin.time.Duration

fun main() {
    var totalDuration = Duration.ZERO

    for (i in 1..31) {
        try {
            val clazz = Any::class::class.java.classLoader.loadClass("com.dennie170.challenges.Day$i")

            val day = clazz.getConstructor().newInstance() as Day<*>

            day.setUp()

            totalDuration += runDayMeasured(day)
        } catch (_: ClassNotFoundException) {
        }
    }

    println("${yellow}TOTAL TIME TAKEN: $blue$totalDuration")
}

