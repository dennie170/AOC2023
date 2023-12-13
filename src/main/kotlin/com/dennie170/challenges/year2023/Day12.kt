package com.dennie170.challenges.year2023

import com.dennie170.Day
import java.util.regex.Pattern

class Day12 : Day<Int>(2023, 12) {

    private lateinit var records: Sequence<String>

    override fun setUp() {
        records = super.readInput().lineSequence()
    }


    override fun part1(): Int {


        return records.map {
            val data = it.split(' ')
            val (a, b, c) = data[1].split(',').map(String::toInt)

            Pair(data[0].toCharArray(), mapOf('a' to a, 'b' to b, 'c' to c))
        }.sumOf(::countArrangements)
    }

    /**
     * Split in groups -> then check how many times we can satify each group
     * Result = group a * group b * group c
     */
    private val groupPattern = Pattern.compile("(?<a>[\\?#]+)\\.+(?<b>[\\?#]+)\\.+(?<c>[\\?#]+)")

    private fun countArrangements(record: Pair<CharArray, Map<Char, Int>>): Int {
        val (chars, contract) = record

        var possibilities = 0

        val matcher = groupPattern.matcher(String(chars)).apply { find() }

        for (group in matcher.namedGroups()) {

            val key = group.key
            val index = group.value


            possibilities += getGroupPossibilities(contract[key.first()]!!, matcher.group(index))
        }



        return possibilities
    }


    private fun getGroupPossibilities(brokenItemCount: Int, input: String): Int {
        return -1
    }


    override fun part2(): Int {
        TODO("Not yet implemented")
    }
}
