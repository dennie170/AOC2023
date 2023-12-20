package com.dennie170.challenges.year2023

import com.dennie170.Day


class Day20 : Day<Long>(2023, 20) {
    companion object {
        private val EMPTY_PULSE_MAP = mapOf(Pulse.LOW to 0L, Pulse.HIGH to 0L)
    }

    private lateinit var input: Sequence<String>

    private lateinit var modules: Map<String, Module>

    private lateinit var flipflopsEnabled: MutableMap<String, Boolean>

    private lateinit var conjuntionMemory: MutableMap<String, Pulse>


    private val cache = mutableMapOf<String, Map<Pulse, Long>>()

    override fun setUp() {
        input = super.readInput().lineSequence()
    }

    override fun part1(): Long {
        modules = input.map(Module::parse).associateBy { it.name }
        flipflopsEnabled = modules.filter { it.value.type == SwitchType.FLIP_FLOP }.map { it.key to false }.toMap().toMutableMap()
        conjuntionMemory = modules.filter { it.value.type == SwitchType.CONJUNCTION }.map { it.key to Pulse.LOW }.toMap().toMutableMap()

        for (m in modules.keys) {
            cache[m] = mutableMapOf()
        }

        var totalHigh = 0L
        var totalLow = 0L

        for(i in 0..<1000) {
            val result = runModule("broadcaster", Pulse.LOW, EMPTY_PULSE_MAP)
            totalLow += result[Pulse.LOW]!!
            totalHigh += result[Pulse.HIGH]!!
        }


        return totalLow * totalHigh
    }

    private fun isEnabled(flipflop: String): Boolean = flipflopsEnabled[flipflop]!!

    private fun allConjuntionPulsesAreHigh(): Boolean = conjuntionMemory.all { it.value == Pulse.HIGH }


    // 216000000 -> too low
    // 205000000 -> too low
    // 185000000 -> too low
    private fun runModule(
        name: String,
        pulse: Pulse,
        totalPulses: Map<Pulse, Long>
    ): Map<Pulse, Long> {

        val module = modules[name] ?: return mapOf(
            Pulse.LOW to if(pulse == Pulse.LOW) 1L else 0L,
            Pulse.HIGH to if(pulse == Pulse.HIGH) 1L else 0L,
        )

        var newPulse = pulse

        if (module.type == SwitchType.FLIP_FLOP) {
            if (pulse == Pulse.HIGH) return mapOf(Pulse.LOW to 0, Pulse.HIGH to 1)

            val enabled = isEnabled(name)

            newPulse = if (!enabled) Pulse.HIGH else Pulse.LOW

            flipflopsEnabled[name] = !enabled
        } else if (module.type == SwitchType.CONJUNCTION) {
            conjuntionMemory[name] = pulse

            newPulse = if (allConjuntionPulsesAreHigh()) Pulse.LOW else Pulse.HIGH
        }

        if(cache[name]!!.containsKey(pulse)) {
            return cache[name]!!
        }

        var destinationsLow = 0L
        var destinationsHigh = 0L

        if (pulse == Pulse.LOW) {
            destinationsLow++
        } else {
            destinationsHigh++
        }

        for (m in module.destinations) {
            val result = runModule(m, newPulse, EMPTY_PULSE_MAP)
            destinationsLow += result[Pulse.LOW]!!
            destinationsHigh += result[Pulse.HIGH]!!
        }

        val result = mapOf(
            Pulse.LOW to (totalPulses[Pulse.LOW]!! + destinationsLow),
            Pulse.HIGH to (totalPulses[Pulse.HIGH]!! + destinationsHigh),
        )

        cache[name] = result

        return result
    }

    override fun part2(): Long {
        return -1
    }

    private data class Module(val name: String, val type: SwitchType, val destinations: List<String>) {
        companion object {
            fun parse(line: String): Module {
                val parts = line.split(" -> ")
                val name = parts[0].replace("%", "").replace("&", "")
                val type = when (parts[0][0]) {
                    '%' -> SwitchType.FLIP_FLOP
                    '&' -> SwitchType.CONJUNCTION
                    else -> SwitchType.BROADCASTER
                }

                val destinations = parts[1].split(',').map { it.trim() }

                return Module(name, type, destinations)
            }
        }
    }


    enum class SwitchType {
        BROADCASTER,
        FLIP_FLOP,
        CONJUNCTION,
    }

    enum class Pulse {
        LOW,
        HIGH;

        fun inverse(): Pulse {
            return if (this == LOW) HIGH else LOW
        }
    }

}
