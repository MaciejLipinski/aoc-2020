package days

class Day15 : Day(15) {
    override fun partOne(): Any {
        val startingNumbers = inputList.map { it.toLong() }
        val numbers = mutableListOf<Long>()
        numbers.addAll(startingNumbers)
        var lastNum = numbers[startingNumbers.size - 1]
        for (i in (startingNumbers.size + 1)..2020) {
            if (!numbers.subList(0, i - 3).contains(lastNum)) {
                numbers.add(0)
            } else {
                val turnLastSpoken = numbers.subList(0, i - 2).lastIndexOf(lastNum) + 1
                numbers.add(i - 1L - turnLastSpoken)
            }
            lastNum = numbers.last()
        }
        return numbers[2020 - 1]
    }

    override fun partTwo(): Any {
        val startingNumbers = inputList.map { it.toLong() }
        val numbersMap = mutableMapOf<Long, Pair<Long, Long>>()
        startingNumbers.forEachIndexed { index: Int, l: Long ->
            numbersMap[l] = Pair(index + 1L, 0)
        }
        var prevNum = startingNumbers[startingNumbers.size - 1]
        for (turn in (startingNumbers.size + 1)..30000000) {
            val lastTurnOfPrevNum = numbersMap[prevNum]
            if (lastTurnOfPrevNum == null || lastTurnOfPrevNum.second == 0L) {
                val prevTurnOfZero = numbersMap[0]?.first
                numbersMap[0] = Pair(turn.toLong(), prevTurnOfZero ?: 0)
                prevNum = 0
            } else {
                val currentNum = lastTurnOfPrevNum.first - lastTurnOfPrevNum.second
                val currentNumPrevTurn = numbersMap[currentNum]?.first ?: 0
                numbersMap[currentNum] = Pair(turn.toLong(), currentNumPrevTurn)
                prevNum = currentNum
            }
        }
        return prevNum
    }

}