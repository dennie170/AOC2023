package com.dennie170.challenges.year2023

import com.dennie170.Day

class Day13 : Day<Int>(2023, 13) {

    private lateinit var input: String

    override fun setUp() {
        input = super.readInput()
    }

    private fun getblocks(): List<List<Block>> {
        return input.split("\n\n").map { it.trim('\n') }.map(::parseBlock).chunked(2)
    }

    private fun parseBlock(block: String): Block {
        val arr = block.toCharArray()
        val width = arr.indexOf('\n')
        val height = arr.count { it == '\n' } + 1

        val matrix: Array<Array<Char>> = Array(height) { Array(width) { '0' } }

        val chars = arr.filter { it != '\n' }

        for (line in 0..<height) {
            for (char in 0..<width) {

                // Fill the matrix with the correct chars
                matrix[line][char] = chars[char + (line * width)]
            }
        }

        return matrix
    }

    private fun calculateReflectionValue(blocks: List<Block>): Int {

        val (first, second) = blocks

        val firstResult = getReflection(first)
        val secondResult = getReflection(second)

        return (firstResult + 1) + (secondResult + 2)
    }

    private fun getReflection(block: Block): Int {

        // Find vertical line
        val columns = mutableListOf<List<Char>>()

        for (col in block[0].indices) {
            val column = mutableListOf<Char>()
            for (row in block.indices) {
                column.add(block[row][col])
            }
            columns.add(column)
        }


        val verticallyMirroredColumn = getMirroredColumn(columns)

        // vertical verticallyMirroredColumn - (columns.size / 2)
        val horizontallyMirroredColumn = getMirroredColumn(block.toList().map { it.toList() })


        return if (verticallyMirroredColumn - (columns.size / 2) > horizontallyMirroredColumn - (block.size / 2)) {
            (100 * (verticallyMirroredColumn))

        } else {
            horizontallyMirroredColumn

        }



        throw IllegalArgumentException("No reflection found")
    }

    // 42695 -> too high
    // 25568 -> too low

    private fun getMirroredColumn(columns: List<List<Char>>): Int {

        // Loop 1 (0..9 -> 9) (start to end)
        for (i in columns.indices) {

            if (columns.indices.contains(i + 1)) {
                if (columns[i] == columns[i + 1]) {
                    return i
                }
            }

        }

        return 0
    }


    override fun part1(): Int {
        return getblocks().sumOf(::calculateReflectionValue)
    }


    override fun part2(): Int {
        TODO("Not yet implemented")
    }

}

typealias Block = Array<Array<Char>>
