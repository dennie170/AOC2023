package com.dennie170.challenges.year2023

import com.dennie170.Day

class Day4 : Day<Int>(2023, 4) {

    data class Card(val number: Int, val winningNumbers: Set<Int>, val yourNumbers: Set<Int>) {
        private var cardCountMatch: Int? = null

        fun calculateScore(): Int {
            val matches = yourNumbers.intersect(winningNumbers)

            if (matches.isEmpty()) {
                return 0
            }

            return matches.toMutableList().apply { this.remove(0) }
                .fold(1) { acc: Int, _: Int ->
                    acc * 2
                }
        }

        fun getRecursiveScratchCardsCount(cards: List<Card>): Int {
            if(cardCountMatch != null) return cardCountMatch!!

            val matches = yourNumbers.intersect(winningNumbers)

            if (matches.isEmpty()) {
                return 0
            }

            val range = (number + 1).rangeUntil(number + 1 + matches.size)

            val nextCards = cards.filter { range.contains(it.number) }

            if(nextCards.isEmpty()) return matches.size

            cardCountMatch = nextCards.fold(matches.size) { acc, card ->
                acc + card.getRecursiveScratchCardsCount(cards)
            }

            return cardCountMatch!!
        }
    }

    private lateinit var input: Sequence<String>

    override fun setUp() {
        input = super.readInput().lineSequence()
    }

    override fun part1(): Int {
        return input.map(::stringToCard)
            .map(Card::calculateScore)
            .fold(0) { acc, i ->
                acc + i
            }
    }

    private fun stringToCard(input: String): Card {
        val id = input.substringBefore(':').substring(5).trim().toInt()
        val winningNumbers = input.substringAfter(':').substringBefore('|').split(' ').filter(String::isNotEmpty).map(String::toInt).toSet()
        val yourNumbers = input.substringAfter('|').split(' ').filter(String::isNotEmpty).map(String::toInt).toSet()

        return Card(id, winningNumbers, yourNumbers)

    }

    override fun part2(): Int {
        val cards = input.map(::stringToCard).toList()


        return cards.fold(cards.size) { acc, card ->  acc + card.getRecursiveScratchCardsCount(cards) }

    }
}
