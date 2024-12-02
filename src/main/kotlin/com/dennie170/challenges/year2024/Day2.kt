package com.dennie170.challenges.year2024

import com.dennie170.Day

class Day2 : Day<Int>(2024, 2) {

    val lines: List<String> = readInput().lines()

    override fun part1(): Int {
        val reports = lines.map { it.split(' ') }.map {
            it.map { r -> r.toInt() }
        }

        var mode: Char? = null
        var unsafeReports = 0

        for (report in reports) {

            for (i in 0..report.size) {
                if(i+1 == report.size) break

                if (report[i] == report[i + 1]) {
                    unsafeReports++
                    break
                }

                if (report[i + 1] > report[i]) {
                    if(mode == 'd') {
                        unsafeReports++
                        break
                    }

                    mode = 'i'
                    if (report[i + 1] > report[i] + 3) {
                        unsafeReports++
                        break
                    }
                } else if (report[i + 1] < report[i]) {
                    if(mode == 'i') {
                        unsafeReports++
                        break
                    }

                    mode = 'd'
                    if (report[i + 1] < report[i] - 3) {
                        unsafeReports++
                        break
                    }
                }
            }
            mode = null
        }

        return reports.size - unsafeReports
    }


    override fun part2(): Int {
        return -1
    }
}
