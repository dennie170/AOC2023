package com.dennie170.challenges.year2023

import com.dennie170.Day
import com.dennie170.common.getMatrix

class Day16 : Day<Int>(2023, 16) {

    private lateinit var input: String
    private lateinit var matrix: Array<Array<Char>>

    override fun setUp() {
        input = super.readInput()
        matrix = getMatrix(input.filter { it != '\n' }.toCharArray())
    }

    override fun part1(): Int {
        shootBeam(Coordinate(0, 0), Direction.RIGHT)

        return energizedTiles.map {
            it.first
        }.distinct().size
    }

    private val energizedTiles = mutableSetOf<Pair<Coordinate, Direction>>()

    private fun drawTiles() {
        val drawMatrix = Array(matrix.size) { Array(matrix[0].size) { '.' } }

        for (tile in energizedTiles) {
            drawMatrix[tile.first.row][tile.first.col] = '#'
        }

        for (row in drawMatrix.indices) {
            println()
            for (col in drawMatrix[0].indices) {
                print(drawMatrix[row][col])
            }
        }
        println()
    }

    private fun shootBeam(coordinate: Coordinate, direction: Direction) {
        return if (direction.isHorizontal()) shootBeamHorizontally(coordinate, direction) else shootBeamVertically(coordinate, direction)
    }

    private fun shootBeamHorizontally(coordinate: Coordinate, direction: Direction) {
        val range = if (direction == Direction.RIGHT) coordinate.col..<matrix[coordinate.row].size else coordinate.col downTo 0

        for (col in range) {
            val char = matrix[coordinate.row][col]
            val tuple = Pair(Coordinate(coordinate.row, col), direction)

            if (energizedTiles.contains(tuple)) {
                // We've been here before. Bail
                break
            }

            energizedTiles.add(tuple)
            when (char) {
                '|' -> {
                    // shoot 2 more beams up and down
                    shootBeamVertically(Coordinate(coordinate.row + 1, col), Direction.DOWN)
                    shootBeamVertically(Coordinate(coordinate.row - 1, col), Direction.UP)
                    break
                }

                '\\' -> {
                    // redirect beam down
                    if (direction == Direction.RIGHT) {
                        shootBeamVertically(Coordinate(coordinate.row + 1, col), Direction.DOWN)
                    } else {
                        shootBeamVertically(Coordinate(coordinate.row - 1, col), Direction.UP)
                    }
                    break
                }

                '/' -> {
                    // redirect beam up
                    if (direction == Direction.RIGHT) {
                        shootBeamVertically(Coordinate(coordinate.row - 1, col), Direction.UP)
                    } else {
                        shootBeamVertically(Coordinate(coordinate.row + 1, col), Direction.DOWN)
                    }
                    break
                }
            }
        }
    }

    private fun shootBeamVertically(coordinate: Coordinate, direction: Direction) {
        val range = if (direction == Direction.DOWN) coordinate.row..<matrix.size else coordinate.row downTo 0

        for (row in range) {
            val char = matrix[row][coordinate.col]
            val tuple = Pair(Coordinate(row, coordinate.col), direction)

            if (energizedTiles.contains(tuple)) {
                // We've been here before. Bail
                break
            }
            energizedTiles.add(tuple)


            when (char) {
                '-' -> {
                    // shoot 2 beams horizontally
                    shootBeamHorizontally(Coordinate(row, coordinate.col - 1), Direction.LEFT)
                    shootBeamHorizontally(Coordinate(row, coordinate.col + 1), Direction.RIGHT)
                    break
                }

                '\\' -> {
                    // redirect beam left
                    if (direction == Direction.UP) {
                        shootBeamHorizontally(Coordinate(row, coordinate.col - 1), Direction.LEFT)
                    } else {
                        shootBeamHorizontally(Coordinate(row, coordinate.col + 1), Direction.RIGHT)
                    }
                    break
                }

                '/' -> {
                    //redirect beam right
                    if (direction == Direction.UP) {
                        shootBeamHorizontally(Coordinate(row, coordinate.col + 1), Direction.RIGHT)
                    } else {
                        shootBeamHorizontally(Coordinate(row, coordinate.col - 1), Direction.LEFT)
                    }
                    break
                }
            }
        }
    }

    override fun part2(): Int {
        var max = 0

        for (col in matrix[0].indices) {
            shootBeam(Coordinate(0, col), Direction.DOWN)

            val round = energizedTiles.map {
                it.first
            }.distinct().size

            if (round > max) {
                max = round
            }

            energizedTiles.clear()
        }

        for (row in matrix.indices) {
            shootBeam(Coordinate(row, matrix[0].size - 1), Direction.LEFT)

            val round = energizedTiles.map {
                it.first
            }.distinct().size

            if (round > max) {
                max = round
            }

            energizedTiles.clear()
        }


        return max
    }

    enum class Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT;

        fun isHorizontal() = this == RIGHT || this == LEFT
    }

    data class Coordinate(val row: Int, val col: Int)
}
