package days

class Day3 : Day(3) {
    override fun partOne(): Any {
        val tobogganMap = inputList.map { mapLineFrom(it) }
        val width = tobogganMap[0].size
        var treeCount = 0

        for (i in tobogganMap.indices) {
            val y = i
            val x =
                    if (i == 0) {
                        0
                    } else if (3 * i < width) {
                        3 * i
                    } else {
                        (3 * i).rem(width)
                    }

            if (tobogganMap[y][x]) treeCount++
        }

        return treeCount
    }

    override fun partTwo(): Any {
        val tobogganMap = inputList.map { mapLineFrom(it) }
        val width = tobogganMap[0].size
        val pathsYX = listOf(1 to 1, 1 to 3, 1 to 5, 1 to 7, 2 to 1)
        var treeCount = 0
        var result = 1L

        for (path in pathsYX) {
            val yStep = path.first
            val xStep = path.second
            for (i in tobogganMap.indices step yStep) {
                val y = i
                val x =
                        if (i == 0) {
                            0
                        } else if (xStep * i < width) {
                            if (xStep >= yStep) {
                                xStep * i
                            } else {
                                xStep * i / yStep
                            }
                        } else {
                            (if (xStep >= yStep) {
                                xStep * i
                            } else {
                                xStep * i / yStep
                            }).rem(width)
                        }

                if (tobogganMap[y][x]) treeCount++
            }
            result *= treeCount
            treeCount = 0
        }
        return result
    }

    private fun mapLineFrom(inputLine: String) = inputLine.map { it == '#' }
}