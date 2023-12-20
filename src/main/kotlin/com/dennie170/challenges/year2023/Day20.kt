package com.dennie170.challenges.year2023

import com.dennie170.Day
import java.util.*


class Day20 : Day<Long>(2023, 20) {
    companion object {
        private val EMPTY_PULSE_MAP = mapOf(Pulse.LOW to 0L, Pulse.HIGH to 0L)
    }

    private lateinit var input: Sequence<String>

    private lateinit var modules: Map<String, Module>

    private lateinit var flipflopsEnabled: MutableMap<String, Boolean>

    private lateinit var conjuntionMemory: MutableMap<String, MutableMap<String, Pulse>>

    private val queue: Queue<QueueItem> = LinkedList()


    private val cache = mutableMapOf<String, Map<Pulse, Long>>()

    override fun setUp() {
        input = super.readInput().lineSequence()
    }

    private var totalHigh = 0L
    private var totalLow = 0L

    override fun part1(): Long {
        modules = input.map(Module::parse).associateBy { it.name }
        flipflopsEnabled = modules.filter { it.value.type == SwitchType.FLIP_FLOP }.map { it.key to false }.toMap().toMutableMap()
        conjuntionMemory = modules.filter { it.value.type == SwitchType.CONJUNCTION }
            .map { con ->
                con.key to modules.filter { m ->
                    m.value.destinations.contains(con.key)
                }.map { m ->
                    m.key to Pulse.LOW
                }.toMap().toMutableMap()
            }.toMap().toMutableMap()
            .toMutableMap()

        for (m in modules.keys) {
            cache[m] = mutableMapOf()
        }

        var previousFlipflop: String? = null

        for (i in 0..<1000) {
            queue.offer(QueueItem("button", Pulse.LOW))

            while (queue.size > 0) {
                val item = queue.poll()

                sendPulse(previousFlipflop, item.module, item.pulse)

                if (modules.containsKey(item.module)) {
                    if (modules[item.module]!!.type == SwitchType.FLIP_FLOP) {
                        previousFlipflop = item.module
                    }
                }
            }
        }

        return totalLow * totalHigh
    }

    private fun sendPulse(from: String?, name: String, pulse: Pulse){
        if (name == "button") {
            queue.offer(QueueItem("broadcaster", pulse))
            return
        }


        if(pulse == Pulse.LOW) {
            totalLow++
        } else {
            totalHigh++
        }

        val module = modules[name] ?: return

        var newPulse = pulse

        if (module.type == SwitchType.FLIP_FLOP) {
            if (pulse == Pulse.HIGH) return

            val enabled = isEnabled(name)

            newPulse = if (!enabled) Pulse.HIGH else Pulse.LOW

            flipflopsEnabled[name] = !enabled

        } else if (module.type == SwitchType.CONJUNCTION) {
            if (conjuntionMemory[name]!!.containsKey(from!!)) {
                conjuntionMemory[name]!![from] = pulse
            }

            newPulse = if (allConjuntionPulsesAreHigh(module.name)) Pulse.LOW else Pulse.HIGH

            // See if this is an inverter
            if (isInverter(module.name)) {
                newPulse = if (pulse == Pulse.LOW) Pulse.HIGH else Pulse.LOW
            }
        }

        for (m in module.destinations) {
            queue.offer(QueueItem(m, newPulse))
        }

        return
    }

    private fun isEnabled(flipflop: String): Boolean = flipflopsEnabled[flipflop]!!

    private fun allConjuntionPulsesAreHigh(module: String): Boolean = conjuntionMemory[module]?.all { it.value == Pulse.HIGH } ?: false

    private fun isInverter(module: String): Boolean = modules.values.flatMap { it.destinations }.count { it == module } == 1


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


    private enum class SwitchType {
        BROADCASTER, FLIP_FLOP, CONJUNCTION,
    }

    private enum class Pulse {
        LOW, HIGH;
    }

    private data class QueueItem(val module: String, val pulse: Pulse)
}
