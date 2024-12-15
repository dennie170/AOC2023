package com.dennie170.challenges.year2024

import com.dennie170.Day
import com.dennie170.common.Coordinates
import kotlin.math.sqrt

class Day15 : Day<Int>(2024, 15) {

    private fun getMatrix(input: CharArray): Array<CharArray> {
        // Calculate total line length and lines to draw matrix
        val sqrt = sqrt(input.size.toDouble()).toInt()

        val matrix: Array<CharArray> = Array(sqrt) { CharArray(sqrt) { '0' } }

        for (line in 0..<sqrt) {
            for (char in 0..<sqrt) {

                // Fill the matrix with the correct chars
                matrix[line][char] = input[char + (line * sqrt)]
            }
        }

        return matrix
    }


    private val boxes = mutableSetOf<Coordinates>()

    private lateinit var currentPosition: Coordinates


    private lateinit var walls: Set<Coordinates>

    enum class Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT,
    }

    private fun walk(direction: Direction) {
        return when (direction) {
            Direction.UP -> walkUp()
            Direction.DOWN -> walkDown()
            Direction.LEFT -> walkLeft()
            Direction.RIGHT -> walkRight()
        }
    }

    private fun walkUp() {
        val newPosition = Coordinates(currentPosition.row - 1, currentPosition.col)

        // Check if wall is up
        if (walls.contains(newPosition)) {
            return
        }

        // Check if boxes are up
        if (boxes.contains(newPosition)) {
            if (moveBoxUp(newPosition)) {
                currentPosition = newPosition
            }
            return
        }

        // Walk up
        currentPosition = newPosition
    }

    private fun walkDown() {
        val newPosition = Coordinates(currentPosition.row + 1, currentPosition.col)
        // Check if wall is up
        if (walls.contains(newPosition)) {
            return
        }

        if (boxes.contains(newPosition)) {
            if (moveBoxDown(newPosition)) {
                currentPosition = newPosition
            }
            return
        }

        currentPosition = newPosition
    }

    private fun walkLeft() {
        val newPosition = Coordinates(currentPosition.row, currentPosition.col - 1)
        // Check if wall is on the left
        if (walls.contains(newPosition)) {
            return
        }

        // Check if boxes are on the left

        if (boxes.contains(newPosition)) {
            if (moveBoxLeft(newPosition)) {
                currentPosition = newPosition
            }
            return
        }


        // Walk left
        currentPosition = newPosition
    }

    private fun walkRight() {
        val newPosition = Coordinates(currentPosition.row, currentPosition.col + 1)

        // Check if wall is on the right
        if (walls.contains(newPosition)) {
            return
        }


        if (boxes.contains(newPosition)) {
            if (moveBoxRight(newPosition)) {
                currentPosition = newPosition
            }
            return
        }

        currentPosition = newPosition
    }

    // Move box located on position to a spot on the right, recursively
    private fun moveBoxRight(position: Coordinates): Boolean {
        val box = boxes.find { it == position }

        if (box == null) {
            return true
        }

        val newPosition = Coordinates(position.row, position.col + 1)

        if (walls.contains(newPosition)) {
            return false
        } else if (boxes.contains(newPosition)) {
            if (moveBoxRight(newPosition)) {
                boxes.remove(box)
                boxes.add(newPosition)
                return true
            } else {
                return false
            }
        } else {
            boxes.remove(box)
            boxes.add(newPosition)
            return true
        }

    }

    // Move box located on position to a spot on the left, recursively
    private fun moveBoxLeft(position: Coordinates): Boolean {
        val box = boxes.find { it == position }

        if (box == null) {
            return true
        }

        val newPosition = Coordinates(position.row, position.col - 1)

        if (walls.contains(newPosition)) {
            return false
        } else if (boxes.contains(newPosition)) {
            if (moveBoxLeft(newPosition)) {
                boxes.remove(box)
                boxes.add(newPosition)
                return true
            } else {
                return false
            }
        } else {
            boxes.remove(box)
            boxes.add(newPosition)
            return true
        }
    }

    // Move box located on position to a spot down, recursively
    private fun moveBoxDown(position: Coordinates): Boolean {
        val box = boxes.find { it == position }

        if (box == null) {
            return true
        }

        val newPosition = Coordinates(position.row + 1, position.col)

        if (walls.contains(newPosition)) {
            return false
        } else if (boxes.contains(newPosition)) {
            if (moveBoxDown(newPosition)) {
                boxes.remove(box)
                boxes.add(newPosition)
                return true
            } else {
                return false
            }
        } else {
            boxes.remove(box)
            boxes.add(newPosition)
            return true
        }
    }

    // Move box located on position to a spot down, recursively
    private fun moveBoxUp(position: Coordinates): Boolean {
        val box = boxes.find { it == position }

        if (box == null) {
            return true
        }

        val newPosition = Coordinates(position.row - 1, position.col)

        if (walls.contains(newPosition)) {
            return false
        } else if (boxes.contains(newPosition)) {
            if (moveBoxUp(newPosition)) {
                boxes.remove(box)
                boxes.add(newPosition)
                return true
            } else {
                return false
            }
        } else {
            boxes.remove(box)
            boxes.add(newPosition)
            return true
        }
    }

    override fun part1(): Int {
        val input = readInput().split("\n\n")

        val matrix = getMatrix(input[0].filterNot { it == '\n' }.toCharArray())
        val instructions = input[1].toCharArray().filterNot { it == '\n' }.map {
            when (it) {
                '^' -> Direction.UP
                'v' -> Direction.DOWN
                '<' -> Direction.LEFT
                '>' -> Direction.RIGHT
                else -> throw IllegalStateException("Impossible direction '$it'.")
            }
        }

        val walls = mutableSetOf<Coordinates>()
        for (row in matrix.indices) {
            for (col in matrix[row].indices) {
                when {
                    matrix[row][col] == '@' -> currentPosition = Coordinates(row, col)
                    matrix[row][col] == '#' -> walls.add(Coordinates(row, col))
                    matrix[row][col] == 'O' -> boxes.add(Coordinates(row, col))
                }
            }
        }

        this.walls = walls

        for (instruction in instructions) {
            walk(instruction)
        }


        return boxes.fold(0) { acc, box ->
            acc + ((100 * box.row) + box.col)
        }
    }

    private fun draw(matrix: Array<CharArray>) {
        for (row in matrix.indices) {
            for (col in matrix[row].indices) {
                when {
                    currentPosition == Coordinates(row, col) -> print('@')
                    walls.contains(Coordinates(row, col)) -> print('#')
                    boxes.contains(Coordinates(row, col)) -> print('O')
                    else -> print('.')
                }
            }
            println()
        }
    }

    override fun part2(): Int {
        TODO()
    }
}
