package com.dennie170.challenges.year2023

import com.dennie170.Day

class Day7 : Day<Int>(2023, 7) {

    private lateinit var input: String

    override fun setUp() {
        input = super.readInput()
    }

    companion object {
        val strengthMap = mapOf(
            'A' to "13",
            'K' to "12",
            'Q' to "11",
            'J' to "10",
            'T' to "09",
            '9' to "08",
            '8' to "07",
            '7' to "06",
            '6' to "05",
            '5' to "04",
            '4' to "03",
            '3' to "02",
            '2' to "01",
        )

        val part2StrengthMap = mapOf(
            'A' to "13",
            'K' to "12",
            'Q' to "11",
            'T' to "10",
            '9' to "09",
            '8' to "08",
            '7' to "07",
            '6' to "06",
            '5' to "05",
            '4' to "04",
            '3' to "03",
            '2' to "02",
            'J' to "01",
        )
    }

    private fun getCards() = input.lineSequence().map(::mapCard)

    private fun mapCard(input: String): Hand {
        val cards = input.substring(0, 5).toCharArray().map(::Card)
        val bid = input.substring(6, input.length).toInt()

        return Hand(cards, bid)
    }

    override fun part1(): Int {
        return getCards()
            .sortedWith(compareBy<Hand> { it.strength }.thenBy { it.cardsWeight })
            .mapIndexed { index, strength -> strength.bidAmount * (index + 1) }.reduce { acc, i -> acc + i }
    }

    override fun part2(): Int {
        return getCards()
            .sortedWith(compareBy<Hand> { it.part2Strength }.thenBy { it.part2CardWeight })
            .mapIndexed { index, strength -> strength.bidAmount * (index + 1) }.reduce { acc, i -> acc + i }
    }

    data class Hand(val cards: List<Card>, val bidAmount: Int) {

        private fun isFiveOfAKind(): Boolean {
            return cards.distinct().size == 1
        }

        private fun isFourOfAKind(): Boolean {
            return cards.groupBy { it }.maxBy { it.value.size }.value.size == 4
        }

        private fun isFullHouse(): Boolean {
            val maxed = cards.groupBy { it }.values.sortedByDescending { it.size }
            return maxed[0].size == 3 && maxed[1].size == 2
        }

        private fun isThreeOfAKind(): Boolean {
            return cards.groupBy { it }.maxBy { it.value.size }.value.size == 3
        }

        private fun isTwoPairs(): Boolean {
            return cards.groupBy { it }.count { it.value.size == 2 } == 2
        }

        private fun isPair(): Boolean {
            return cards.groupBy { it }.count { it.value.size == 2 } == 1
        }

        private fun isHighCard(): Boolean {
            return cards.distinct().size == cards.size
        }

        private fun getJokerCount(): Int {
            return cards.count { it.value == 'J' }
        }

        val strength: Strength by lazy {
            if (isFiveOfAKind()) {
                Strength.FIVE_OF_KIND
            } else if (isFourOfAKind()) {
                Strength.FOUR_OF_KIND
            } else if (isFullHouse()) {
                Strength.FULL_HOUSE
            } else if (isTwoPairs()) {
                Strength.TWO_PAIRS
            } else if (isThreeOfAKind()) {
                Strength.THREE_OF_KIND
            } else if (isPair()) {
                Strength.ONE_PAIR
            } else if (isHighCard()) {
                Strength.HIGH_CARD
            } else {
                throw IllegalStateException()
            }
        }

        val part2Strength: Strength by lazy {
            val jokers = getJokerCount()

            // Skip if no jokers
            when (jokers) {
                0 -> strength
                1 -> when (strength) {
                    Strength.HIGH_CARD -> Strength.ONE_PAIR
                    Strength.ONE_PAIR -> Strength.THREE_OF_KIND
                    Strength.TWO_PAIRS -> Strength.FULL_HOUSE
                    Strength.THREE_OF_KIND -> Strength.FOUR_OF_KIND
                    Strength.FULL_HOUSE -> Strength.FOUR_OF_KIND
                    Strength.FOUR_OF_KIND, Strength.FIVE_OF_KIND -> Strength.FIVE_OF_KIND
                }

                2 -> when (strength) {
                    Strength.THREE_OF_KIND, Strength.FULL_HOUSE, Strength.FOUR_OF_KIND, Strength.FIVE_OF_KIND -> Strength.FIVE_OF_KIND
                    Strength.TWO_PAIRS -> Strength.FOUR_OF_KIND
                    Strength.ONE_PAIR, Strength.HIGH_CARD -> Strength.THREE_OF_KIND
                }

                3 -> when (strength) {
                    Strength.ONE_PAIR -> Strength.FIVE_OF_KIND
                    Strength.FULL_HOUSE -> Strength.FIVE_OF_KIND
                    else -> Strength.FOUR_OF_KIND
                }

                else -> Strength.FIVE_OF_KIND
            }
        }

        val cardsWeight: String by lazy {
            cards.fold("") { acc, card ->
                acc + strengthMap[card.value]!!
            }
        }

        val part2CardWeight: String by lazy {
            cards.fold("") { acc, card ->
                acc + part2StrengthMap[card.value]!!
            }
        }
    }

    @JvmInline
    value class Card(val value: Char)

    enum class Strength(val value: Int) {
        HIGH_CARD(1),
        ONE_PAIR(2),
        TWO_PAIRS(3),
        THREE_OF_KIND(4),
        FULL_HOUSE(5),
        FOUR_OF_KIND(6),
        FIVE_OF_KIND(7),
    }

}
