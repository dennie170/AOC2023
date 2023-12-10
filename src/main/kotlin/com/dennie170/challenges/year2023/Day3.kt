package com.dennie170.challenges.year2023

import com.dennie170.Day
import com.dennie170.common.getMatrix
import kotlin.math.sqrt

class Day3 : Day<Int>(2023, 3) {
    private lateinit var input: CharArray

    override fun setUp() {
        input = super.readInput().replace("\n", "").toCharArray()
    }

    private lateinit var matrix: Array<Array<Char>>

    override fun part1(): Int {
        val matrix = getMatrix(input)

        val numbers = mutableMapOf<Int, Char>()
        val characters = mutableMapOf<Int, Char>()

        var index = 0
        for (line in matrix) {
            for (i in matrix.indices) {
                // Increment for every character to fill number and character maps
                index++

                if (line[i] == '.') continue

                if (line[i].isDigit()) {
                    numbers[index - 1] = line[i]
                }

                if (!line[i].isDigit()) {
                    characters[index - 1] = line[i]
                }
            }
        }

        val flattenedNumbers = mutableMapOf<Int, String>()

        var inx = 0
        for (i in numbers) {
            if (inx != 0 && i.key < inx + 1) continue;
            inx = i.key

            var num = i.value.toString()

            while (numbers.containsKey(inx + 1)) {
                num += numbers[inx + 1]
                inx++
            }

            flattenedNumbers[i.key] = num
        }

        // Find adjacent numbers

        val adjacentNumbers = flattenedNumbers.filter { (key, value) ->
            (0..value.length).any {
                characters.containsKey(key + it) ||
                        characters.containsKey((key + it) + matrix.indices.count()) ||
                        characters.containsKey((key + it) - matrix.indices.count()) ||

                        characters.containsKey((key - 1) + matrix.indices.count()) ||
                        characters.containsKey((key - 1) - matrix.indices.count()) ||
                        characters.containsKey(key - 1)
            }
        }

        return adjacentNumbers.toList().map { it.second.toInt() }.reduce { acc, num ->
            acc + num
        }
    }

    data class Vector2(val row: Int, val col: Int)

    override fun part2(): Int {
        matrix = getMatrix(input)
        val matrixSize = matrix.indices.count()

        val asterisks = mutableListOf<Vector2>()

        for(row in 0..< matrixSize) {
            for (col in 0..< matrixSize) {
                if(matrix[row][col] == '*') {
                    asterisks.add(Vector2(row, col))
                }
            }
        }

        return asterisks.map {
            getGearRatioFromAsterisk(it)
        }.reduce { acc, i ->
            acc + i
        }
    }

    private fun getGearRatioFromAsterisk(asterisk: Vector2): Int {
        val result = mutableListOf<Int>()

        val rows = (asterisk.row - 1 .. asterisk.row + 1)
        val columns = (asterisk.col -1 .. asterisk.col + 1)

        for (row in rows) {
            var foundDigit = false
            for (col in columns) {
                if(matrix.getOrNull(row) == null || matrix[row].getOrNull(col) == null) {
                    continue
                }
                val char = matrix[row][col]

                if(char.isDigit()) {
                    if(!foundDigit) {
                        result.add(readNumber(row, col))
                    }
                    foundDigit = true
                } else {
                    foundDigit = false
                }
            }
        }

        if(result.size < 2) {
            return 0
        }

        return result[0] * result[1]
    }

    private fun readNumber(row: Int, col: Int): Int {
        val sb = StringBuilder()

        // Read left first
        var currentColumn = col

        try {
            do {
                if(!matrix[row][currentColumn].isDigit()) break

                sb.insert(0, matrix[row][currentColumn]) // prepend
                currentColumn--
            } while(matrix[row][currentColumn] != null)

            currentColumn = col + 1

            do {
                if(!matrix[row][currentColumn].isDigit()) break

                sb.append(matrix[row][currentColumn]) // append
                currentColumn++
            } while(matrix[row][currentColumn] != null)
        } catch (_: IndexOutOfBoundsException) {
        }

        val int = sb.toString().toInt()
        sb.clear()

        return int
    }

}
