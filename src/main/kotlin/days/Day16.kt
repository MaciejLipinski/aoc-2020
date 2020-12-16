package days

class Day16 : Day(16) {
    override fun partOne(): Any {
        val rules = inputString
                .split("your ticket:")[0]
                .split("\n")
                .filter { it.isNotBlank() }
                .map { it.split(": ")[1] }
                .map { it.split(" or ") }
                .flatMap {
                    it
                            .map { inner -> inner.split("-") }
                            .map { inner -> inner.map { num -> num.toInt() } }
                            .map { inner -> IntRange(inner[0], inner[1]) }
                }

        val ticketNumbers = inputString
                .split("nearby tickets:")[1]
                .split("\n")
                .filter { it.isNotBlank() }
                .map { it.split(",") }
                .flatMap {
                    it.map { inner -> inner.toInt() }
                }

        return ticketNumbers
                .filter { t -> !rules.any { r -> r.contains(t) } }
                .sum()
    }

    override fun partTwo(): Any {
        val rules = inputString
                .split("your ticket:")[0]
                .split("\n")
                .filter { it.isNotBlank() }
                .map { it.split(": ") }
                .map {
                    it[0] to it[1]
                            .split(" or ")
                            .map { inner -> inner.split("-") }
                            .map { inner -> inner.map { num -> num.toInt() } }
                            .map { inner -> IntRange(inner[0], inner[1]) }
                }

        val tickets = inputString
                .split("nearby tickets:")[1]
                .split("\n")
                .filter { it.isNotBlank() }
                .map { it.split(",") }
                .map { it.map { inner -> inner.toInt() } }
                .filter { t -> !t.any { i -> !rules.any { r -> r.second.any { range -> range.contains(i) } } } }

        val possibleRuleAssignments = mutableMapOf<Int, MutableList<String>>()

        for (i in tickets[0].indices) {
            rules
                    .forEach { rule ->
                        if (tickets.all { ticket -> rule.second.any { it.contains(ticket[i]) } }) {
                            if (possibleRuleAssignments[i] == null) {
                                possibleRuleAssignments[i] = mutableListOf(rule.first)
                            } else {
                                possibleRuleAssignments[i]!!.add(rule.first)
                            }
                        }
                    }
        }

        val sortedRules = possibleRuleAssignments.toList().sortedBy { it.second.size }
        sortedRules.subList(0, sortedRules.lastIndex).forEachIndexed { i, possibleRuleNames ->
            possibleRuleNames.second.forEach { ruleName ->
                sortedRules.subList(i + 1, sortedRules.lastIndex + 1).forEach { otherPossibleRuleNames ->
                    otherPossibleRuleNames.second.remove(ruleName)
                }
            }
        }

        if (sortedRules.any { it.second.size != 1 }) throw RuntimeException()

        val assignedRules = sortedRules.map { it.first to it.second.first() }.toMap()

        val myTicket =
                inputString
                        .split("your ticket:")[1]
                        .split("nearby tickets:")[0]
                        .split("\n")
                        .filter { it.isNotBlank() }
                        .reduce { acc, s -> "$acc$s" }
                        .split(",")
                        .map { it.toLong() }

        return myTicket
                .filterIndexed { index, _ -> assignedRules[index]?.startsWith("departure") ?: false }
                .reduce { acc, i -> acc * i }
    }
}