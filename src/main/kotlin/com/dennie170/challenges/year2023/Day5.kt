package com.dennie170.challenges.year2023

import com.dennie170.Day
import java.util.regex.Pattern

class Day5 : Day<Long>(2023, 5) {
    private lateinit var input: String

    override fun setUp() {
        input = super.readInput()
    }

    override fun part1(): Long {
        val almanac = Almanac.parse(input)

        var seeds = almanac.seeds

        for (map in almanac.maps) {
            seeds = seeds.map {
                mapItemToNext(it, map.value)
            }
        }

        return seeds.min()
    }

    private fun mapItemToNext(item: Long, map: List<Range>): Long {
        val sources = map.map {
            it.source.rangeUntil(it.source + it.length)
        }

        val destinations = map.map {
            it.destination.rangeUntil(it.destination + it.length)
        }

        val destinationIndex = sources.indexOfFirst { it.contains(item) }

        if (destinationIndex == -1) return item

        val index = item - sources[destinationIndex].first

        val added = destinations[destinationIndex].first + index

        return if (destinations[destinationIndex].contains(added)) added else item
    }

    override fun part2(): Long {
        // Disabled because this takes way too long
        return -1L
        // val almanac = Almanac.parse(input)

        // var seeds = almanac.seeds.chunked(2).map { it.first().rangeUntil(it.first() + it.last()) }.flatMap {
        //     it.toList()
        // }

        // for (map in almanac.maps) {
        //     seeds = seeds.map {
        //         mapItemToNext(it, map.value)
        //     }
        // }

        // return seeds.min()
    }


    data class Almanac(val seeds: List<Long>, val maps: Map<String, List<Range>>) {
        companion object {
            fun parse(input: String): Almanac {
                val seeds = input.substring(7)
                    .substringBefore('\n')
                    .split(' ')
                    .map(String::toLong)

                val maps = mutableMapOf<String, List<Range>>()
                val matcher = Pattern.compile("([a-z-]+ map:\\n[0-9\\s]+)").matcher(input)

                while (matcher.find()) {
                    val lines = matcher.group(1).lines()
                    val name = lines.removeFirst().substringBefore(' ')
                    val ranges = lines.filter(String::isNotEmpty).map {
                        val line = it.split(' ').filter(String::isNotEmpty)
                        Range(line[0].toLong(), line[1].toLong(), line[2].toInt())
                    }

                    maps[name] = ranges
                }

                return Almanac(seeds, maps)
            }
        }
    }


    data class Range(val destination: Long, val source: Long, val length: Int)
}
