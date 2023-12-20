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

    // 32000000 -> correct
    // 16500000
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

        var totalHigh = 0L
        var totalLow = 0L

//        for (i in 0..<1000) {
        queue.offer(QueueItem("button", Pulse.LOW))

        var previousFlipflop: String? = null

        while (queue.size > 0) {
            val item = queue.poll()

            val result = sendPulse(previousFlipflop, item.module, item.pulse)

            totalLow += result[Pulse.LOW]!!
            totalHigh += result[Pulse.HIGH]!!

            if (modules.containsKey(item.module)) {
                if (modules[item.module]!!.type == SwitchType.FLIP_FLOP) {
                    previousFlipflop = item.module
                }
            }
        }

        return totalLow * totalHigh
    }

    private fun sendPulse(from: String?, name: String, pulse: Pulse): Map<Pulse, Long> {
        if (name == "button") {
            queue.offer(QueueItem("broadcaster", pulse))
            return Pulse.map(1L, 0L)
        }

        val module = modules[name] ?: return Pulse.map(
            if (pulse == Pulse.LOW) 1L else 0L,
            if (pulse == Pulse.HIGH) 1L else 0L,
        )

        var newPulse = pulse

        if (module.type == SwitchType.FLIP_FLOP) {
            if (pulse == Pulse.HIGH) return Pulse.map(0L, 1L)

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


//        if (cache[name]!!.containsKey(pulse)) {
//            return cache[name]!!
//        }

        for (m in module.destinations) {
            println("[${module.name}] -> $newPulse : $m")

            queue.offer(QueueItem(m, newPulse))
        }

        val lowPulses = if (newPulse == Pulse.LOW) module.destinations.size.toLong() else 0L
        val highPulses = if (newPulse == Pulse.HIGH) module.destinations.size.toLong() else 0L


        return Pulse.map(
            if (pulse == Pulse.LOW) lowPulses + 1 else 0,
            if (pulse == Pulse.HIGH) highPulses + 1 else 0,
        )
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

        companion object {
            fun map(low: Long, high: Long) = mapOf(LOW to low, HIGH to high)
        }
    }

    private data class QueueItem(val module: String, val pulse: Pulse)
}
