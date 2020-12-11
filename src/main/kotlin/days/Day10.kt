package days

import kotlin.math.pow

class Day10 : Day(10) {
    override fun partOne(): Any {
        val adapters = (inputList.map { it.toInt() } + 0).sorted()
        var differencesOf1Jolt = 0
        var differencesOf3Jolts = 1
        for (i in 0 until adapters.lastIndex) {
            if (adapters[i + 1] - adapters[i] == 1) differencesOf1Jolt++
            if (adapters[i + 1] - adapters[i] == 3) differencesOf3Jolts++
        }
        return differencesOf1Jolt * differencesOf3Jolts
    }

    override fun partTwo(): Any {
        val adapters = (inputList.map { it.toInt() } + 0).sorted().toMutableList()
        adapters.add(adapters.maxOrNull()!! + 3)
        val removableIndexes = mutableListOf<Int>()
        for (i in 1 until adapters.lastIndex) {
            if (adapters[i - 1] + 3 >= adapters[i + 1]) removableIndexes.add(i)
        }
        var tripletsCount = 0
        var i = 0
        while (i < removableIndexes.size - 2) {
            if (removableIndexes[i] + 1 == removableIndexes[i + 1]
                    && removableIndexes[i] + 2 == removableIndexes[i + 2]) {
                tripletsCount++
                i += 3
            } else {
                i++
            }
        }
        val singleRemovablesCount = removableIndexes.size - 3 * tripletsCount
        return 2.0.pow(singleRemovablesCount).toLong() * 7.0.pow(tripletsCount).toLong()
    }
}