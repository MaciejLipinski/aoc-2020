package days

class Day6 : Day(6) {
    override fun partOne(): Any {
        return splitIntoGroups(inputString)
                .map { FormGroup.from(it) }
                .map { it.distinctAnswers() }
                .map { it.count() }
                .sum()
    }

    override fun partTwo(): Any {
        return splitIntoGroups(inputString)
                .map { FormGroup.from(it) }
                .map { it.commonAnswers() }
                .map { it.count() }
                .sum()
    }

    private fun splitIntoGroups(input: String): List<String> {
        val groups = mutableListOf<String>()
        val groupLines = mutableListOf<String>()

        for (i in inputList.indices) {
            if (inputList[i].isBlank()) {
                groups.add(groupLines.reduce { acc, s -> "$acc $s" })
                groupLines.clear()
                continue
            }
            groupLines.add(inputList[i])
        }
        groups.add(groupLines.reduce { acc, s -> "$acc $s" })

        return groups
    }
}

data class FormGroup(val forms: List<Form>) {

    fun distinctAnswers(): List<Char> = forms.flatMap { it.answers }.distinct()

    fun commonAnswers(): List<Char> = distinctAnswers().filter { forms.all { form -> form.answers.contains(it) } }

    companion object {
        fun from(input: String): FormGroup =
            FormGroup(input.split(" ").map { Form(it.toCharArray().toList()) })
    }
}

data class Form(val answers: List<Char>)