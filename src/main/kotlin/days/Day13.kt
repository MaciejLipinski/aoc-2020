package days

import java.time.Duration
import java.time.Instant
import kotlin.math.ceil

class Day13 : Day(13) {
    override fun partOne(): Any {
        val timestamp = inputList[0].toInt()
        return inputList[1]
                .split(",")
                .filter { it != "x" }
                .map { it.toInt() }
                .map { it to findLowestMultipleGte(it, timestamp) }
                .minByOrNull { it.second }
                ?.let { (it.second - timestamp) * it.first }
                ?: throw RuntimeException()
    }

    override fun partTwo(): Any {
        val buses = getBuses()
        val nums = buses.map { it.first.toLong() }
        val maxOffset = buses.maxByOrNull { it.second }?.second!! + 1
        val offsets = buses.map { it.second }.map { maxOffset - it }
        return chineseRemainder(nums, offsets) - maxOffset
    }

    private fun partTwoIterative(): Long {
        val busesWithOffsets = getBuses()
        var multiplier = 1L//210000000000000L
        val timeAtStart = Instant.now()
        outer@ while (true) {
            if (multiplier % 1000000000 == 0L) println("${Duration.between(timeAtStart, Instant.now())} $multiplier")
            for (i in 1..busesWithOffsets.lastIndex) {
                if ((multiplier * busesWithOffsets[0].first + busesWithOffsets[i].second) % busesWithOffsets[i].first != 0L) {
                    if (i == 1) {
                        multiplier++
                    } else {
                        multiplier += busesWithOffsets[i - 1].first
                    }
                    continue@outer
                }
            }
            return multiplier * busesWithOffsets[0].first
        }
    }

    private fun getBuses(): List<Pair<Int, Int>> {
        val tokens = inputList[1].split(",")
        val busesWithOffsets = mutableListOf<Pair<Int, Int>>()
        var offset = 0
        for (i in tokens.indices) {
            if (tokens[i] == "x") {
                offset++
            } else {
                busesWithOffsets.add(Pair(
                        tokens[i].toInt(),
                        (if (busesWithOffsets.isEmpty()) 0 else busesWithOffsets.last().second + 1) + offset
                ))
                offset = 0
            }
        }
        return busesWithOffsets
    }

    private fun findLowestMultipleGte(number: Int, other: Int): Int {
        return ceil(other.toDouble() / number).toInt() * number
    }

    /**
     * This function was copied from https://rosettacode.org/wiki/Chinese_remainder_theorem#Kotlin and then modified.
     * It is licensed under GNU Free Documentation License 1.2.
     * See the file resources/fdl-1.2.txt for copying conditions.
     */
    private fun chineseRemainder(n: List<Long>, a: List<Int>): Long {
        val product = n.fold(1L) { acc, i -> acc * i }
        var sum = 0L
        for (i in n.indices) {
            val p = product / n[i]
            sum += a[i] * p *
                    (if (n[i] == 1L) 1 else {
                        var a1 = p
                        var b1 = n[i]
                        var x0 = 0L
                        var x1 = 1L
                        while (a1 > 1) {
                            val q = a1 / b1
                            var t = b1
                            b1 = a1 % b1
                            a1 = t
                            t = x0
                            x0 = x1 - q * x0
                            x1 = t
                        }
                        if (x1 < 0) x1 += n[i]
                        x1
                    })
        }
        return sum % product
    }
}