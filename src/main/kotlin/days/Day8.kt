package days

import days.Operation.ACC
import days.Operation.JMP
import days.Operation.NOP

class Day8 : Day(8) {
    override fun partOne(): Any {
        val instructions = inputList.map { Instruction.from(it) }
        val alreadyVisited = instructions.map { false }.toMutableList()
        var i = 0
        var accumulator = 0
        while (i < instructions.size) {
            if (alreadyVisited[i]) {
                return accumulator
            }
            alreadyVisited[i] = true
            when (instructions[i].operation) {
                ACC -> {
                    accumulator += instructions[i].argument
                    i++
                }
                JMP -> i += instructions[i].argument
                NOP -> i++
            }
        }
        return -1
    }

    override fun partTwo(): Any {
        possibleChangedInstructions()
                .forEach { instructions ->
                    val alreadyVisited = instructions.map { false }.toMutableList()
                    var i = 0
                    var accumulator = 0
                    while (i < instructions.size) {
                        if (alreadyVisited[i]) {
                            i = Int.MAX_VALUE
                            continue
                        }
                        alreadyVisited[i] = true
                        when (instructions[i].operation) {
                            ACC -> {
                                accumulator += instructions[i].argument
                                i++
                            }
                            JMP -> i += instructions[i].argument
                            NOP -> i++
                        }
                        if (i == instructions.size) {
                            return accumulator
                        }
                    }
                }
        return -1
    }

    private fun possibleChangedInstructions(): List<List<Instruction>> {
        val possibleChangedInstructions = mutableListOf<MutableList<Instruction>>()
        val actualInstructions = inputList.map { Instruction.from(it) }
        var j = 0
        for (i in actualInstructions.indices) {
            if (actualInstructions[i].operation in listOf(JMP, NOP)) {
                possibleChangedInstructions.add(j, actualInstructions.toMutableList())
                if (possibleChangedInstructions[j][i].operation == JMP) {
                    possibleChangedInstructions[j][i] = Instruction(NOP, possibleChangedInstructions[j][i].argument)
                } else {
                    possibleChangedInstructions[j][i] = Instruction(JMP, possibleChangedInstructions[j][i].argument)
                }
                j++
            }
        }
        return possibleChangedInstructions
    }
}

data class Instruction(val operation: Operation, val argument: Int) {

    companion object {
        fun from(input: String): Instruction {
            val operation = Operation.valueOf(input.substring(0..2).toUpperCase())
            val argument = input.substring(4, input.lastIndex + 1).removePrefix("+").toInt()

            return Instruction(operation, argument)
        }
    }
}

enum class Operation {
    ACC, JMP, NOP
}