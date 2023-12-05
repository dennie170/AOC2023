package com.dennie170.challenges

import com.dennie170.Day
import java.util.regex.Pattern
import kotlin.math.abs

class Day5 : Day<Long>(5) {
    private val input = """
        seeds: 79 14 55 13

        seed-to-soil map:
        50 98 2
        52 50 48

        soil-to-fertilizer map:
        0 15 37
        37 52 2
        39 0 15

        fertilizer-to-water map:
        49 53 8
        0 11 42
        42 0 7
        57 7 4

        water-to-light map:
        88 18 7
        18 25 70

        light-to-temperature map:
        45 77 23
        81 45 19
        68 64 13

        temperature-to-humidity map:
        0 69 1
        1 0 69

        humidity-to-location map:
        60 56 37
        56 93 4
    """.trimIndent()

    override fun part1(): Long {
        val almanac = Almanac.parse(input)


        var seeds = almanac.seeds

        for (map in almanac.maps) {
            seeds = seeds.map {
                mapItemToNext(it, map.value )
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

        if(destinationIndex == -1) return item

        val index  = sources[destinationIndex].indexOf(item)

        return destinations[destinationIndex].toList().getOrNull(index) ?: return item
    }

    override fun part2(): Long {
        return 0
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

                while(matcher.find()) {
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
