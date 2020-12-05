package days

import kotlin.math.pow

class Day5 : Day(5) {
    override fun partOne(): Any {
        return inputList
                .map { BoardingPass(it).getSeatId() }
                .maxOrNull()
                ?: throw RuntimeException()
    }

    override fun partTwo(): Any {
        return possibleSeats()
                .minus(inputList.map { BoardingPass(it).getSeatId() })
    }

    private fun possibleSeats(): List<Int> {
        val possibleSeats = mutableListOf<Int>()
        (0..127).forEach { row ->
            (0..7).forEach { column ->
                possibleSeats.add(row * 8 + column)
            }
        }
        return possibleSeats
    }
}

data class BoardingPass(val code: String) {

    fun getSeatId(): Int =
            getRow() * 8 + getColumn()

    fun getRow(): Int {
        val rowCode = code.substring(0..6)

        return rowCode.foldIndexed(0, {index: Int, acc: Int, c: Char ->
            acc + if(c == 'B') (2.0.pow(6 - index)).toInt() else 0
        })
    }

    fun getColumn(): Int {
        val columnCode = code.substring(7..9)

        return columnCode.foldIndexed(0, {index: Int, acc: Int, c: Char ->
            acc + if(c == 'R') (2.0.pow(2 - index)).toInt() else 0
        })
    }
}