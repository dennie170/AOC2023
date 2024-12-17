package com.dennie170.challenges.year2024

import com.dennie170.Day
import com.dennie170.challenges.year2024.Day17.Opcode.*
import kotlin.math.pow


class Day17 : Day<String>(2024, 17) {


    private fun parseProgram(input: String): List<Opcode> {
        val lines = input.lines()

        registerA = lines[0].substring(12).toInt()
        registerB = lines[1].substring(12).toInt()
        registerC = lines[2].substring(12).toInt()

        return lines.last().substring(9).split(',').map { Opcode.fromInt(it.toInt()) }
    }


    enum class Opcode {
        ADV,
        BXL,
        BST,
        JNZ,
        BXC,
        OUT,
        BDV,
        CDV;

        companion object {
            fun fromInt(int: Int): Opcode {
                return when (int) {
                    0 -> ADV
                    1 -> BXL
                    2 -> BST
                    3 -> JNZ
                    4 -> BXC
                    5 -> OUT
                    6 -> BDV
                    7 -> CDV
                    else -> throw IllegalStateException("Illegal opcode $int")
                }
            }
        }
    }

    private var registerA: Int = 0
    private var registerB: Int = 0
    private var registerC: Int = 0

    private var instructionPointer = 0

    private val result = mutableListOf<Int>()

    override fun part1(): String {
        val program = parseProgram(readInput())

        while (true) {
            val previousPoint = instructionPointer

            if (instructionPointer + 1 !in program.indices) {
                break
            }

            handleOpcode(program[instructionPointer], program[instructionPointer + 1].ordinal)

            if (program[previousPoint] == JNZ && previousPoint != instructionPointer) {
                continue
            }

            instructionPointer += 2
        }

        return result.joinToString(",") { it.toString() }
    }


    private fun getComboOperand(opcode: Int): Int {
        return when (opcode) {
            0 -> 0
            1 -> 1
            2 -> 2
            3 -> 3
            4 -> registerA
            5 -> registerB
            6 -> registerC
            else -> throw IllegalStateException("Illegal. CDV is reserved.")
        }
    }

    private fun handleOpcode(opcode: Opcode, input: Int) {
        when (opcode) {
            ADV -> {
                val denominator = getComboOperand(input)
                registerA = (registerA / (2.0.pow(denominator))).toInt()
            }

            BXL -> {
                registerB = registerB xor input


            }

            BST -> {
                registerB = getComboOperand(input).mod(8)

            }

            JNZ -> {
                if (registerA == 0) return

                instructionPointer = input

            }

            BXC -> {
                registerB = registerB xor registerC

            }

            OUT -> {
                result.add(getComboOperand(input).mod(8))

            }

            BDV -> {
                val denominator = getComboOperand(input)
                registerB = (registerA / (2.0.pow(denominator))).toInt()

            }

            CDV -> {
                val denominator = getComboOperand(input)
                registerC = (registerA / (2.0.pow(denominator))).toInt()

            }
        }
    }


    override fun part2(): String {
        TODO()
    }


}







