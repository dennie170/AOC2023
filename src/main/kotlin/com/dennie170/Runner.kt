package com.dennie170

import kotlin.time.Duration

class Runner {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
//            runAll()
            runDay(2024, 14)
//           runYear(2024)
        }

        private fun runAll() {
            var totalDuration = Duration.ZERO

            for (year in 2022..2024) {
                totalDuration += runYear(year)
            }

            println("${yellow}TOTAL TIME TAKEN: $blue$totalDuration")
        }

        private fun runDay(year: Int, day: Int): Duration {
            val clazz = Any::class::class.java.classLoader.loadClass("com.dennie170.challenges.year${year}.Day$day")
            val dayInstance = clazz.getConstructor().newInstance() as Day<*>

            return runDayMeasured(dayInstance)
        }

        private fun runYear(year: Int): Duration {
            var yearDuration = Duration.ZERO

            for (day in 1..31) {
                try {
                    yearDuration += runDay(year, day)
                } catch (_: ClassNotFoundException) {
                }
            }

            println("${yellow}Year $year took: $blue$yearDuration")

            return yearDuration
        }

    }

}
