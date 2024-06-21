package com.dennie170

import kotlin.time.Duration

class Runner {
    companion object {
       @JvmStatic fun main(args: Array<String>) {
//            runAll()
             runDay(2022, 11)
        }

        private fun runAll() {
            var totalDuration = Duration.ZERO

            for (year in 2022..2023) {
                var yearDuration = Duration.ZERO

                for (day in 1..31) {
                    try {
                        yearDuration += runDay(year, day)
                    } catch (_: ClassNotFoundException) {
                    }
                }

                println("${yellow}Year $year took: $blue$yearDuration")

                totalDuration += yearDuration
            }

            println("${yellow}TOTAL TIME TAKEN: $blue$totalDuration")
        }

        private fun runDay(year: Int, day: Int): Duration {
            val clazz = Any::class::class.java.classLoader.loadClass("com.dennie170.challenges.year${year}.Day$day")
            val dayInstance = clazz.getConstructor().newInstance() as Day<*>

            dayInstance.setUp()

            return runDayMeasured(dayInstance)
        }

    }

}
