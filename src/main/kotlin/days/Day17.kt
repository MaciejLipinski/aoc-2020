package days

class Day17 : Day(17) {
    override fun partOne(): Any {
        val initialState = inputList
                .map {
                    it
                            .mapIndexed { index, char -> index to (char == '#') }
                            .filter { it.second }
                            .toMap().toMutableMap()
                }
                .mapIndexed { index, mutableMap -> index to mutableMap }.toMap().toMutableMap()
                .let { mutableMapOf(0 to it) }

        val previousStates = mutableListOf(initialState)
        for (cycle in 1..6) {
            val newState = mutableMapOf<Int, MutableMap<Int, MutableMap<Int, Boolean>>>()
            val minZ = (previousStates.last().keys.minOrNull() ?: 0) - 1
            val maxZ = (previousStates.last().keys.maxOrNull() ?: 0) + 1
            val minY = (previousStates.last().flatMap { it.value.keys }.minOrNull() ?: 0) - 1
            val maxY = (previousStates.last().flatMap { it.value.keys }.maxOrNull() ?: 0) + 1
            val minX = (previousStates.last().flatMap { it.value.values }.flatMap { it.keys }.minOrNull() ?: 0) - 1
            val maxX = (previousStates.last().flatMap { it.value.values }.flatMap { it.keys }.maxOrNull() ?: 0) + 1
            for (z in minZ..maxZ) {
                for (y in minY..maxY) {
                    for (x in minX..maxX) {
                        val activeNeighbors =
                                previousStates.last()
                                        .filterKeys { it in (z - 1)..(z + 1) }
                                        .flatMap { it.value.toList() }
                                        .filter { it.first in (y - 1)..(y + 1) }
                                        .flatMap { it.second.toList() }
                                        .filter { it.first in (x - 1)..(x + 1) }
                                        .count()

                        val isActive = previousStates.last()[z]?.get(y)?.get(x) ?: false

                        if ((isActive && activeNeighbors - 1 in 2..3) || (!isActive && activeNeighbors == 3)) {
                            if (newState[z] == null) newState[z] = mutableMapOf()
                            if (newState[z]!![y] == null) newState[z]!![y] = mutableMapOf()
                            newState[z]!![y]!![x] = true
                        }
                    }
                }
            }
//            visualize3D(previousStates.last())
            previousStates.add(newState)
        }

        return previousStates
                .last()
                .flatMap { it.value.toList() }
                .flatMap { it.second.toList() }
                .count()
    }

    override fun partTwo(): Any {
        val initialState = inputList
                .map {
                    it
                            .mapIndexed { index, char -> index to (char == '#') }
                            .filter { it.second }
                            .toMap().toMutableMap()
                }
                .mapIndexed { index, mutableMap -> index to mutableMap }.toMap().toMutableMap()
                .let { mutableMapOf(0 to it) }
                .let { mutableMapOf(0 to it) }

        val previousStates = mutableListOf(initialState)
        for (cycle in 1..6) {
            val newState = mutableMapOf<Int, MutableMap<Int, MutableMap<Int, MutableMap<Int, Boolean>>>>()
            val minW = (previousStates.last().keys.minOrNull() ?: 0) - 1
            val maxW = (previousStates.last().keys.maxOrNull() ?: 0) + 1
            val minZ = (previousStates.last().flatMap { it.value.keys }.minOrNull() ?: 0) - 1
            val maxZ = (previousStates.last().flatMap { it.value.keys }.maxOrNull() ?: 0) + 1
            val minY = (previousStates.last().flatMap { it.value.values }.flatMap { it.keys }.minOrNull() ?: 0) - 1
            val maxY = (previousStates.last().flatMap { it.value.values }.flatMap { it.keys }.maxOrNull() ?: 0) + 1
            val minX = (previousStates.last().flatMap { it.value.values }.flatMap { it.values }.flatMap { it.keys }.minOrNull()
                    ?: 0) - 1
            val maxX = (previousStates.last().flatMap { it.value.values }.flatMap { it.values }.flatMap { it.keys }.maxOrNull()
                    ?: 0) + 1
            for (w in minW..maxW) {
                for (z in minZ..maxZ) {
                    for (y in minY..maxY) {
                        for (x in minX..maxX) {
                            val activeNeighbors =
                                    previousStates.last()
                                            .filterKeys { it in (w - 1)..(w + 1) }
                                            .flatMap { it.value.toList() }
                                            .filter { it.first in (z - 1)..(z + 1) }
                                            .flatMap { it.second.toList() }
                                            .filter { it.first in (y - 1)..(y + 1) }
                                            .flatMap { it.second.toList() }
                                            .filter { it.first in (x - 1)..(x + 1) }
                                            .count()

                            val isActive = previousStates.last()[w]?.get(z)?.get(y)?.get(x) ?: false

                            if ((isActive && activeNeighbors - 1 in 2..3) || (!isActive && activeNeighbors == 3)) {
                                if (newState[w] == null) newState[w] = mutableMapOf()
                                if (newState[w]!![z] == null) newState[w]!![z] = mutableMapOf()
                                if (newState[w]!![z]!![y] == null) newState[w]!![z]!![y] = mutableMapOf()
                                newState[w]!![z]!![y]!![x] = true
                            }
                        }
                    }
                }
            }
//            visualize4D(previousStates.last())
            previousStates.add(newState)
        }

        return previousStates
                .last()
                .flatMap { it.value.toList() }
                .flatMap { it.second.toList() }
                .flatMap { it.second.toList() }
                .count()
    }

    private fun visualize3D(map: Map<Int, Map<Int, Map<Int, Boolean>>>) {
        val minZ = (map.keys.minOrNull() ?: 0)
        val maxZ = (map.keys.maxOrNull() ?: 0)
        val minY = (map.flatMap { it.value.keys }.minOrNull() ?: 0)
        val maxY = (map.flatMap { it.value.keys }.maxOrNull() ?: 0)
        val minX = (map.flatMap { it.value.values }.flatMap { it.keys }.minOrNull() ?: 0)
        val maxX = (map.flatMap { it.value.values }.flatMap { it.keys }.maxOrNull() ?: 0)

        for (z in minZ..maxZ) {
            println("z=$z")
            for (y in minY..maxY) {
                for (x in minX..maxX) {
                    val isActive = map[z]?.get(y)?.get(x) ?: false
                    print(if (isActive) "#" else ".")
                    print(" ")
                }
                println()
            }
            println()
        }
    }

    private fun visualize4D(map: Map<Int, Map<Int, Map<Int, Map<Int, Boolean>>>>) {
        val minW = map.keys.minOrNull() ?: 0
        val maxW = map.keys.maxOrNull() ?: 0
        val minZ = map.flatMap { it.value.keys }.minOrNull() ?: 0
        val maxZ = map.flatMap { it.value.keys }.maxOrNull() ?: 0
        val minY = map.flatMap { it.value.values }.flatMap { it.keys }.minOrNull() ?: 0
        val maxY = map.flatMap { it.value.values }.flatMap { it.keys }.maxOrNull() ?: 0
        val minX = map.flatMap { it.value.values }.flatMap { it.values }.flatMap { it.keys }.minOrNull() ?: 0
        val maxX = map.flatMap { it.value.values }.flatMap { it.values }.flatMap { it.keys }.maxOrNull() ?: 0

        for (w in minW..maxW) {
            println("w=$w")
            for (z in minZ..maxZ) {
                println("z=$z")
                for (y in minY..maxY) {
                    for (x in minX..maxX) {
                        val isActive = map[w]?.get(z)?.get(y)?.get(x) ?: false
                        print(if (isActive) "#" else ".")
                        print(" ")
                    }
                    println()
                }
                println()
            }
            println()
        }
    }
}