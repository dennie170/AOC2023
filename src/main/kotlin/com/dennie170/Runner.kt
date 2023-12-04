package com.dennie170

import kotlin.time.Duration

class Runner {
    companion object {
       @JvmStatic fun main(args: Array<String>) {
            runAll()
            // runDay(4)
        }

        private fun runAll() {
            var totalDuration = Duration.ZERO

            for (i in 1..31) {
                try {
                    totalDuration += runDay(i)
                } catch (_: ClassNotFoundException) {
                }
            }

            println("${yellow}TOTAL TIME TAKEN: $blue$totalDuration")
        }

        private fun runDay(day: Int): Duration {
            val clazz = Any::class::class.java.classLoader.loadClass("com.dennie170.challenges.Day$day")
            val dayInstance = clazz.getConstructor().newInstance() as Day<*>

            dayInstance.setUp()

            return runDayMeasured(dayInstance)
        }

    }

}
