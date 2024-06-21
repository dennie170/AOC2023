package com.dennie170.challenges.year2022

import com.dennie170.Day
import com.dennie170.common.getMatrix

class Day8 : Day<Int>(2022, 8) {

    private lateinit var forest: Array<IntArray>

    override fun setUp() {
        forest = getMatrix(super.readInput().replace("\n", "").toCharArray().map { it.digitToInt() })
    }

    data class Tree(val row: Int, val col: Int)

    override fun part1(): Int {
        val spottedTrees = mutableSetOf<Tree>()

        // Top to bottom
        for (column in forest.indices) {
            var tallestHeight = -1

            for (row in forest[0].indices) {
                val tree = forest[row][column]

                if (tree > tallestHeight) {
                    tallestHeight = tree
                    spottedTrees.add(Tree(row, column))
                }
            }
        }

        // Right to left
        for (row in forest.indices) {
            var tallestHeight = -1

            for (column in forest[0].size -1 downTo 0) {
                val tree = forest[row][column]

                if (tree > tallestHeight) {
                    tallestHeight = tree
                    spottedTrees.add(Tree(row, column))
                }
            }
        }

        // Bottom to top
        for (column in forest.indices) {
            var tallestHeight = -1

            for (row in forest.size -1  downTo 0) {
                val tree = forest[row][column]

                if (tree > tallestHeight) {
                    tallestHeight = tree
                    spottedTrees.add(Tree(row, column))
                }
            }
        }

        // Left to right
        for (row in forest.indices) {
            var tallestHeight = -1

            for (column in forest[0].indices) {
                val tree = forest[row][column]

                if (tree > tallestHeight) {
                    tallestHeight = tree
                    spottedTrees.add(Tree(row, column))
                }
            }
        }

        return spottedTrees.size
    }

    override fun part2(): Int {
        return -1
    }
}
