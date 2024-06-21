package com.dennie170.challenges.year2022

import com.dennie170.Day
import com.dennie170.common.getMatrix

class Day8 : Day<Int>(2022, 8) {

    private lateinit var forest: Array<IntArray>

    override fun setUp() {
        forest = getMatrix(super.readInput().replace("\n", "").toCharArray().map { it.digitToInt() })
    }

    data class Tree(val row: Int, val col: Int, val height: Int)

    override fun part1(): Int {
        val spottedTrees = mutableSetOf<Tree>()

        // Top to bottom
        for (column in forest.indices) {
            var tallestHeight = -1

            for (row in forest[0].indices) {
                val tree = forest[row][column]

                if (tree > tallestHeight) {
                    tallestHeight = tree
                    spottedTrees.add(Tree(row, column, tree))
                }
            }
        }

        // Right to left
        for (row in forest.indices) {
            var tallestHeight = -1

            for (column in forest[0].size - 1 downTo 0) {
                val tree = forest[row][column]

                if (tree > tallestHeight) {
                    tallestHeight = tree
                    spottedTrees.add(Tree(row, column, tree))
                }
            }
        }

        // Bottom to top
        for (column in forest.indices) {
            var tallestHeight = -1

            for (row in forest.size - 1 downTo 0) {
                val tree = forest[row][column]

                if (tree > tallestHeight) {
                    tallestHeight = tree
                    spottedTrees.add(Tree(row, column, tree))
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
                    spottedTrees.add(Tree(row, column, tree))
                }
            }
        }

        return spottedTrees.size
    }

    override fun part2(): Int {
        var maximumScenicScore = 0

        for (row in forest.indices) {
            for (column in forest[0].indices) {

                val height = forest[row][column]
                val tree = Tree(row, column, height)

                val left = getViewingDistanceLookingLeft(tree)
                val right = getViewingDistanceLookingRight(tree)
                val up = getViewingDistanceLookingUp(tree)
                val down = getViewingDistanceLookingDown(tree)

                val scenicScore = left * right * up * down

                if (scenicScore > maximumScenicScore) {
                    maximumScenicScore = scenicScore
                }

            }
        }

        return maximumScenicScore
    }

    private fun getViewingDistanceLookingLeft(tree: Tree): Int {
        var viewingDistance = 0

        for (column in tree.col - 1 downTo 0) {
            val treeToCheck = forest[tree.row][column]
            viewingDistance++
            if (treeToCheck >= tree.height) break
        }

        return viewingDistance
    }

    private fun getViewingDistanceLookingRight(tree: Tree): Int {
        var viewingDistance = 0

        for (column in tree.col + 1..<forest.size) {
            val treeToCheck = forest[tree.row][column]
            viewingDistance++
            if (treeToCheck >= tree.height) break
        }

        return viewingDistance
    }

    private fun getViewingDistanceLookingUp(tree: Tree): Int {
        var viewingDistance = 0

        for (row in tree.row - 1 downTo 0) {
            val treeToCheck = forest[row][tree.col]
            viewingDistance++
            if (treeToCheck >= tree.height) break
        }

        return viewingDistance
    }

    private fun getViewingDistanceLookingDown(tree: Tree): Int {
        var viewingDistance = 0

        for (row in tree.row + 1..<forest.size) {
            val treeToCheck = forest[row][tree.col]
            viewingDistance++
            if (treeToCheck >= tree.height) break
        }

        return viewingDistance
    }


}
