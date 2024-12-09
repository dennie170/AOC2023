package com.dennie170.challenges.year2024

import com.dennie170.Day

class Day9 : Day<Long>(2024, 9) {

    val input: String = readInput()

    private abstract class HddEntity(length: Int) {
        abstract val length: Int

        init {
            if (length < 1) {
                throw IllegalStateException("Entity size can never be smaller than 1")
            }
        }

        abstract fun shrink(): HddEntity
    }

    private data class File(val id: Int, override val length: Int) : HddEntity(length) {
        override fun shrink(): File {
            return copy(length = length - 1)
        }
    }

    private data class FreeSpace(override val length: Int) : HddEntity(length) {
        override fun shrink(): FreeSpace {
            return copy(length = length - 1)
        }
    }

    private class Harddrive : ArrayList<HddEntity>() {
        fun print() {
            for (entity in this) {
                if (entity is File) {
                    print(CharArray(entity.length) { Char(entity.id + 48) }) // Digits start at char 48
                } else print(CharArray(entity.length) { '.' })
            }
            println()

        }

        fun isDefragmentated(): Boolean {
            var lastItem: HddEntity? = null

            for (entity in this) {
                if (lastItem == null) {
                    lastItem = entity
                    continue
                }

                if (lastItem is FreeSpace && entity is File) return false

                lastItem = entity
            }

            return true
        }

        fun removeLastFile(): File {
            for (i in size - 1 downTo 0) {
                if (this[i] is File) {
                    return removeAt(i) as File
                }
            }

            throw IllegalStateException("No file left on HDD?")
        }

        fun removeFirstFreeSpace(): FreeSpace {
            for (i in 0..<size) {
                if (this[i] is FreeSpace) {
                    return removeAt(i) as FreeSpace
                }
            }

            throw IllegalStateException("No free space left on HDD")
        }

    }

    private fun parseHardDriveContents(): Harddrive {
        val hardDrive = Harddrive()
        // 0 = free space
        // 1 = file
        // Always start with a file
        var currentType = 1
        var currentId = 0

        for (length in input.toCharArray().map { it.digitToInt() }) {
            if (length == 0 && currentType == 0) {
                currentType = 1 // Switch back to file and skip. Next File will be placed without space in-between
                continue
            }
            val entity = if (currentType == 1) {
                File(currentId++, length)
            } else FreeSpace(length)

            hardDrive.add(entity)

            currentType = currentType xor 1 // Flip 1 and 0
        }

        return hardDrive
    }


    override fun part1(): Long {
        val hardDrive = parseHardDriveContents()

        while (!hardDrive.isDefragmentated()) {
            // Dequeue the last file from disk
            val lastFile = hardDrive.removeLastFile()

            // Find the first available free space on the left
            val firstFreeSpaceIndex = hardDrive.indexOfFirst { it is FreeSpace }


            if (hardDrive[firstFreeSpaceIndex].length > 1) {
                hardDrive[firstFreeSpaceIndex] = hardDrive[firstFreeSpaceIndex].shrink()
            } else {
                hardDrive.addLast(hardDrive.removeAt(firstFreeSpaceIndex))
            }

            hardDrive.add(firstFreeSpaceIndex, lastFile.copy(length = 1))

            if (lastFile.length > 1) {
                hardDrive.addLast(lastFile.shrink())
            }

        }

        var checksum = 0L
        var currentId = 0
        for (entity in hardDrive) {
            if (entity is File) {
                for (i in 0..<entity.length) {
                    checksum += (currentId++ * entity.id)

                    if (checksum < 0) throw IllegalStateException("KAN NIETTTTT")
                }
            }
        }

        return checksum
    }


    override fun part2(): Long {
        TODO()
    }
}
