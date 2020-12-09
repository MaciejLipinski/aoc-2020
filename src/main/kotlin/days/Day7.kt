package days

class Day7 : Day(7) {
    override fun partOne(): Any {
        val rules = inputList.map { BagRule.from(it) }

        return rules
                .filter { it.canContain("shiny gold", rules) }
                .count()
    }

    override fun partTwo(): Any {
        val rules = inputList.map { BagRule.from(it) }

        return rules
                .first { it.color == "shiny gold" }
                .bagsRequiredInside(rules)
    }
}

data class BagRule(val color: String, val allowedColors: Map<String, Int>) {

    fun canContain(color: String, rules: List<BagRule>): Boolean {
        return if (allowedColors.containsKey(color)) {
            return true
        } else {
            allowedColors.keys.any { allowedColor ->
                rules.first { it.color == allowedColor }
                        .canContain(color, rules)
            }
        }
    }

    fun bagsRequiredInside(rules: List<BagRule>): Int {
        var count = 0
        allowedColors.forEach { (color, bagCount) ->
            count += bagCount + bagCount * rules.first { it.color == color }.bagsRequiredInside(rules)
        }
        return count
    }

    companion object {
        fun from(input: String): BagRule {
            val tokens = input.split(" ")

            val color = tokens[0] + " " + tokens[1]

            if (input.contains("contain no other bags")) {
                return BagRule(color, emptyMap())
            }

            val rules = tokens
                    .subList(4, tokens.lastIndex)
                    .reduce { acc, s -> "$acc $s" }
                    .split(",")
                    .map {
                        val ruleTokens = it.trim().split(" ")
                        val number = ruleTokens[0].toInt()
                        val ruleColor = ruleTokens[1] + " " + ruleTokens[2]
                        ruleColor to number
                    }.toMap()

            return BagRule(color, rules)
        }
    }
}