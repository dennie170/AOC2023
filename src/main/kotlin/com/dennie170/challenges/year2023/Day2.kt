package com.dennie170.challenges.year2023

import com.dennie170.Day
import java.util.regex.Pattern

class Day2 : Day<Int>(2023, 2) {
    private lateinit var input: Sequence<String>

    override fun setUp() {
        input = super.readInput().lineSequence()
    }

    private val maxSet = GameSet(14, 12, 13)

    override fun part1(): Int {
        return input.map(::mapToGame).filter(::isSolvable).map { it.id }.reduce { acc, id ->
            acc + id
        }
    }

    override fun part2(): Int {
        return input.map(::mapToGame).map(::getHighestGameCubes).map {
            it.red * it.green * it.blue
        }.reduce { acc, i ->
            acc + i
        }
    }

    private fun mapToGame(game: String): Game {
        val id = Regex("Game ([0-9]+):.*").matchAt(game, 0)!!.groups[1]!!.value.toInt()

        val sets = game.replace("Game $id: ", "").split(';').map {
            val matcher = Pattern.compile("([0-9]+) ([a-z]+)").matcher(it)

            val cubes = mutableMapOf<String, Int>()

            while (matcher.find()) {
                val cube = matcher.group()

                val color = Regex("([a-z]+)").find(cube)!!.value
                val amount = Regex("([0-9]+)").find(cube)!!.value.toInt()

                cubes[color] = amount
            }

            cubes
        }.map {
            GameSet(it["blue"] ?: 0, it["red"] ?: 0, it["green"] ?: 0)
        }

        return Game(id, sets)
    }

    private fun isSolvable(game: Game): Boolean {
        return game.sets.all(::isSolvable)
    }

    private fun isSolvable(set: GameSet): Boolean {
        return set.blue <= maxSet.blue
                && set.red <= maxSet.red
                && set.green <= maxSet.green
    }

    private fun getHighestGameCubes(game: Game): GameSet {
        val red = game.sets.maxBy { it.red }.red
        val blue = game.sets.maxBy { it.blue }.blue
        val green = game.sets.maxBy { it.green }.green

        return GameSet(blue, red, green)
    }

    data class Game(val id: Int, val sets: List<GameSet>)
    data class GameSet(val blue: Int, val red: Int, val green: Int)
}
