package days

import days.Direction.E
import days.Direction.N
import days.Direction.NE
import days.Direction.NW
import days.Direction.S
import days.Direction.SE
import days.Direction.SW
import days.Direction.W
import kotlin.math.min
import kotlin.reflect.KFunction3

class Day11 : Day(11) {
    override fun partOne(): Any {
        return countAllOccupiedSeats(4, List<List<Boolean?>>::countAdjacentOccupiedSeats)
    }

    override fun partTwo(): Any {
        return countAllOccupiedSeats(5, List<List<Boolean?>>::countOccupiedFirstVisibleSeats)
    }

    private fun countAllOccupiedSeats(threshold: Int, function: KFunction3<List<List<Boolean?>>, Int, Int, Int>): Int {
        val seats = inputList.map { it.toCharArray().map { if (it == 'L') false else null } }
        val previousLayouts = listOf(seats).toMutableList()
        var previousOccupied: Int
        var occupied = 0
        do {
            val previousSeats = previousLayouts.last()
            val newSeats = (0..previousSeats.lastIndex).map { (0..previousSeats[0].lastIndex).map { val a: Boolean? = null; a }.toMutableList() }.toMutableList()
            visualize(previousSeats)
            previousOccupied = occupied
            occupied = 0
            for (i in seats.indices) {
                for (j in seats[0].indices) {
                    if (previousSeats[i][j] == null) {
                        newSeats[i][j] = null
                    } else {
                        val seatOccupied = function.invoke(previousSeats, i, j)
                        if (previousSeats[i][j] == true && seatOccupied >= threshold) {
                            newSeats[i][j] = false
                        } else if (previousSeats[i][j] == false && seatOccupied == 0) {
                            newSeats[i][j] = true
                            occupied++
                        } else {
                            newSeats[i][j] = previousSeats[i][j]
                            if (previousSeats[i][j] == true) occupied++
                        }
                    }
                }
            }
            previousLayouts.add(newSeats)
        } while (previousOccupied != occupied)
        visualize(seats)
        return occupied
    }

    private fun visualize(seats: List<List<Boolean?>>) {
        val mappedSeats = seats.map {
            it.map {
                when (it) {
                    true -> "#"
                    false -> "L"
                    null -> "."
                }
            }
        }
        for (i in mappedSeats.indices) {
            for (j in mappedSeats[0].indices) {
                print(mappedSeats[i][j])
            }
            println()
        }
        println("--------------------")
    }

}

fun List<List<Boolean?>>.countAdjacentOccupiedSeats(i: Int, j: Int): Int {
    var seatsOccupied = 0
    if (i > 0 && j > 0 && this[i - 1][j - 1] == true) seatsOccupied++
    if (i > 0 && this[i - 1][j] == true) seatsOccupied++
    if (i > 0 && j < this[0].lastIndex && this[i - 1][j + 1] == true) seatsOccupied++
    if (j > 0 && this[i][j - 1] == true) seatsOccupied++
    if (j < this[0].lastIndex && this[i][j + 1] == true) seatsOccupied++
    if (i < this.lastIndex && j > 0 && this[i + 1][j - 1] == true) seatsOccupied++
    if (i < this.lastIndex && this[i + 1][j] == true) seatsOccupied++
    if (i < this.lastIndex && j < this[0].lastIndex && this[i + 1][j + 1] == true) seatsOccupied++
    return seatsOccupied
}

fun List<List<Boolean?>>.countOccupiedFirstVisibleSeats(i: Int, j: Int): Int =
        Direction.values()
                .count { this.isFirstVisibleSeatOccupied(i, j, it) }

fun List<List<Boolean?>>.isFirstVisibleSeatOccupied(i: Int, j: Int, direction: Direction): Boolean {
    when (direction) {
        N -> {
            if (i == 0) return false
            else {
                for (k in i - 1 downTo 0) {
                    val seat = this[k][j]
                    if (seat != null) return seat
                }
            }
        }
        S -> {
            if (i == this.lastIndex) return false
            else {
                for (k in i + 1..lastIndex) {
                    val seat = this[k][j]
                    if (seat != null) return seat
                }
            }
        }
        E -> {
            if (j == this[0].lastIndex) return false
            else {
                for (k in j + 1..this[0].lastIndex) {
                    val seat = this[i][k]
                    if (seat != null) return seat
                }
            }
        }
        W -> {
            if (j == 0) return false
            else {
                for (k in j - 1 downTo 0) {
                    val seat = this[i][k]
                    if (seat != null) return seat
                }
            }
        }
        NE -> {
            if (i == 0 && j == this[0].lastIndex) return false
            else {
                val limit = min(i, this[0].lastIndex - j)
                for (k in 1..limit) {
                    val seat = this[i - k][j + k]
                    if (seat != null) return seat
                }
            }
        }
        SE -> {
            if (i == this.lastIndex && j == this[0].lastIndex) return false
            else {
                val limit = min(this.lastIndex - i, this[0].lastIndex - j)
                for (k in 1..limit) {
                    val seat = this[i + k][j + k]
                    if (seat != null) return seat
                }
            }
        }
        SW -> {
            if (i == this.lastIndex && j == 0) return false
            else {
                val limit = min(this.lastIndex - i, j)
                for (k in 1..limit) {
                    val seat = this[i + k][j - k]
                    if (seat != null) return seat
                }
            }
        }
        NW -> {
            if (i == 0 && j == 0) return false
            else {
                val limit = min(i, j)
                for (k in 1..limit) {
                    val seat = this[i - k][j - k]
                    if (seat != null) return seat
                }
            }
        }
    }
    return false
}

enum class Direction {
    N, S, E, W, NE, SE, SW, NW
}