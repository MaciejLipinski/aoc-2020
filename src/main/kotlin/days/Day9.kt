package days

class Day9 : Day(9) {
    override fun partOne(): Any {
        val numbers = inputList.map { it.toLong() }
        for (i in numbers.indices) {
            if (i < PREAMBLE_LENGTH) {
                continue
            }
            if (!isSumOfPrevious(numbers[i], numbers.subList(i - PREAMBLE_LENGTH, i))) {
                return numbers[i]
            }
        }
        return -1
    }

    override fun partTwo(): Any {
        val invalidNumber = partOne().toString().toLong()
        val numbers = inputList.map { it.toLong() }
        return numbers
                .mapNotNull { findContiguousList(it, numbers.subList(numbers.indexOf(it), numbers.lastIndex), invalidNumber) }
                .firstOrNull()
                ?.let { minPlusMaxOf(it) }
                ?: -1
    }

    private fun isSumOfPrevious(num: Long, previous: List<Long>): Boolean {
        previous.forEach { a ->
            previous.forEach { b ->
                if (num == a + b) return true
            }
        }
        return false
    }

    private fun findContiguousList(number: Long, numbers: List<Long>, invalidNumber: Long): List<Long>? {
        var sum = 0L
        for (i in numbers.indices) {
            sum += numbers[i]
            if (sum == invalidNumber) {
                return numbers.subList(0, i+1)
            }
            if (sum > invalidNumber) {
                return null
            }
        }
        return null
    }

    private fun minPlusMaxOf(list: List<Long>): Long = (list.maxOrNull() ?: 0) + (list.minOrNull() ?: 0)

    companion object {
        const val PREAMBLE_LENGTH = 25
    }
}