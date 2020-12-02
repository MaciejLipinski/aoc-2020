package days

class Day2 : Day(2) {
    override fun partOne(): Any {
        return inputList
                .map { PasswordEntry.from(it) }
                .count { it.passwordPolicy.isValid1(it.password) }
    }

    override fun partTwo(): Any {
        return inputList
                .map { PasswordEntry.from(it) }
                .count { it.passwordPolicy.isValid2(it.password) }
    }
}

data class PasswordEntry(
        val passwordPolicy: PasswordPolicy,
        val password: String
) {
    companion object {
        fun from(inputString: String): PasswordEntry {
            val tokens = inputString.split(" ")
            val times = tokens[0].split("-")
            val minTimes = times[0].toInt()
            val maxTimes = times[1].toInt()
            val letter = tokens[1].toCharArray()[0]
            val password = tokens[2]

            return PasswordEntry(PasswordPolicy(minTimes, maxTimes, letter), password)
        }
    }
}

data class PasswordPolicy(
        val minTimes: Int,
        val maxTimes: Int,
        val letter: Char
) {
    fun isValid1(password: String): Boolean =
            password.count { it == letter } in minTimes..maxTimes

    fun isValid2(password: String): Boolean {
        val index1 = minTimes - 1
        val index2 = maxTimes - 1
        return (password[index1] == letter).xor(password[index2] == letter)
    }

}