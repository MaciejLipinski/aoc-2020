package days

import days.InstructionType.E
import days.InstructionType.F
import days.InstructionType.L
import days.InstructionType.N
import days.InstructionType.R
import days.InstructionType.S
import days.InstructionType.W

class Day12 : Day(12) {
    override fun partOne(): Any = interpretInstructions(Ferry1())

    override fun partTwo(): Any = interpretInstructions(Ferry2(Waypoint(10, -1)))

    private fun interpretInstructions(ferry: Ferry): Int =
            inputList
                    .map { FerryInstruction.from(it) }
                    .forEach { ferry.execute(it) }
                    .let { ferry.calculateDistance() }
}

open class Waypoint(open var posX: Int, open var posY: Int) {

    fun moveForward(direction: Direction, units: Int) {
        when (direction) {
            Direction.N -> posY -= units
            Direction.S -> posY += units
            Direction.E -> posX += units
            Direction.W -> posX -= units
            else -> throw UnsupportedOperationException()
        }
    }

    open fun rotateLeft(degrees: Int) {
        rotateRight(360 - degrees)
    }

    open fun rotateRight(degrees: Int) {
        val full90s = degrees / 90
        val directions = listOf(Direction.N, Direction.E, Direction.S, Direction.W)
        when (full90s % directions.size){
            0 -> return
            1 -> { val tmp = posX; posX = -posY; posY = tmp }
            2 -> { posX = -posX; posY = -posY }
            3 -> { val tmp = posX; posX = posY; posY = -tmp }
        }
    }
}

abstract class Ferry(
        override var posX: Int = 0,
        override var posY: Int = 0,
        var facing: Direction = Direction.E
) : Waypoint(posX, posY) {
    abstract fun execute(instruction: FerryInstruction)
    fun calculateDistance(): Int = posX + posY
}

class Ferry1 : Ferry() {

    override fun execute(instruction: FerryInstruction) {
        when (instruction.type) {
            N, S, E, W -> moveForward(Direction.valueOf(instruction.type.toString()), instruction.units)
            L -> rotateLeft(instruction.units)
            R -> rotateRight(instruction.units)
            F -> moveForward(facing, instruction.units)
        }
    }

    override fun rotateLeft(degrees: Int) {
        rotateRight(360 - degrees)
    }

    override fun rotateRight(degrees: Int) {
        val full90s = degrees / 90
        val directions = listOf(Direction.N, Direction.E, Direction.S, Direction.W)
        val startingIndex = directions.indexOf(facing)
        facing = directions[(startingIndex + full90s) % directions.size]
    }
}

class Ferry2(var waypoint: Waypoint) : Ferry() {

    override fun execute(instruction: FerryInstruction) {
        when (instruction.type) {
            N, S, E, W -> waypoint.moveForward(Direction.valueOf(instruction.type.toString()), instruction.units)
            L -> waypoint.rotateLeft(instruction.units)
            R -> waypoint.rotateRight(instruction.units)
            F -> moveTowardWaypoint(instruction.units)
        }
    }

    private fun moveTowardWaypoint(times: Int) {
        posX += times * waypoint.posX
        posY += times * waypoint.posY
    }
}

class FerryInstruction(val type: InstructionType, val units: Int) {

    companion object {
        fun from(input: String): FerryInstruction {
            return FerryInstruction(
                    InstructionType.valueOf(input.substring(0, 1)),
                    input.substring(1, input.lastIndex + 1).toInt()
            )
        }
    }
}

enum class InstructionType {
    N, S, E, W, L, R, F
}