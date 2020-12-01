package days

class Day1 : Day(1) {

    override fun partOne(): Any {
        return inputList
                .map { it.toInt() }
                .mapNotNull { findMatchingFor((inputList.map { i -> i.toInt() } - it), it) }
                .first()
                .itMultipliedByMatching()
    }

    override fun partTwo(): Any {
        val numbers = inputList.map { it.toInt() }

        return numbers
                .flatMap { (numbers - it).map { inner -> Pair(inner, it) } }
                .asSequence()
                .distinct()
                .filter { it.first + it.second < 2020 }
                .mapNotNull { findMatchingFor(numbers, it.toList().sum()) }
                .distinct()
                .reduce { acc, i -> acc * i }
    }

    private fun findMatchingFor(numbers: List<Int>, number: Int): Int? =
        numbers.find { it + number == 2020 }

    private fun Int.itMultipliedByMatching(): Int = this * (2020 - this)

}
