package com.dennie170.challenges.year2022

import com.dennie170.Day

class Day7 : Day<Long>(2022, 7) {

    companion object {
        val LS_REGEX = Regex("^([0-9]+|dir)\\s([0-9a-zA-Z\\_\\-\\.]+)$")
    }

    private lateinit var input: List<String>

    private val cwd = mutableListOf<String>()

    private val fileSystem = Folder("/", mutableSetOf())

    override fun setUp() {
        input = super.readInput().lines()
    }

    private fun isCommand(line: String) = line[0] == '$'


    private fun execute(command: String) {
        if (command.substring(0, 2) == "cd") {
            cd(command.substring(3))
        }
    }

    private fun cd(path: String) {
        if (path == "..") {
            cwd.removeLast()
        } else {
            cwd.addLast(path)
        }
    }

    private fun parseResponse(line: String) {
        val match = LS_REGEX.matchEntire(line)!!

        val filesize = match.groups[1]!!.value
        val filename = match.groups[2]!!.value

        var currentFolder = fileSystem

        if (cwd.size > 1) {
            // Get current folder first
            for (dir in cwd) {
                if (dir == "/") continue
                currentFolder = currentFolder.items.first { it.name == dir } as Folder
            }
        }

        val entity = if (filesize == "dir") Folder(filename) else File(filename, filesize.toLong())
        currentFolder.items.add(entity)
    }

    // 23447523 -> too high
    // 95437 -> too low
    override fun part1(): Long {

        for (line in input) {
            if (isCommand(line)) {
                execute(line.substring(2))
            } else {
                parseResponse(line)
            }
        }

        return fileSystem.flatten().filter { it.getSize() <= 100000 }.sumOf { it.getSize() }
    }

    override fun part2(): Long {
        return -1
    }
}

interface FilesystemObject {
    public val name: String
}

data class File(override val name: String, val size: Long) : FilesystemObject

data class Folder(override val name: String, val items: MutableSet<FilesystemObject> = mutableSetOf()) : FilesystemObject {
    fun flatten(): List<Folder> {
        val folders = mutableListOf(this)

        for (folder in items.filterIsInstance<Folder>()) {
            folders.addAll(folder.flatten())
        }

        return folders
    }

    fun getSize(): Long {
        var total = 0L

        for (item in items) {
            total += if (item is File) {
                item.size
            } else {
                (item as Folder).getSize()
            }
        }

        return total
    }
}
