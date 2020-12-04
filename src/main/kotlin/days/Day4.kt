package days

class Day4 : Day(4) {
    override fun partOne(): Any {
        return splitIntoPassports(inputString)
                .map { Passport.from(it) }
                .count { it.isValid() }
    }

    override fun partTwo(): Any {
        return splitIntoPassports(inputString)
                .map { Passport.from(it) }
                .count { it.isValid2() }
    }

    private fun splitIntoPassports(input: String): List<String> {
        val passports = mutableListOf<String>()
        val passportLines = mutableListOf<String>()

        for (i in inputList.indices) {
            if (inputList[i].isBlank()) {
                passports.add(passportLines.reduce { acc, s -> "$acc $s" })
                passportLines.clear()
                continue
            }
            passportLines.add(inputList[i])
        }
        passports.add(passportLines.reduce { acc, s -> "$acc $s" })

        return passports
    }
}

data class Passport(
        val birthYear: Int?,        //byr (Birth Year)
        val issueYear: Int?,        //iyr (Issue Year)
        val expirationYear: Int?,   //eyr (Expiration Year)
        val height: String?,           //hgt (Height)
        val hairColor: String?,     //hcl (Hair Color)
        val eyeColor: String?,      //ecl (Eye Color)
        val passportId: String?,    //pid (Passport ID)
        val countryId: String?      //cid (Country ID)
) {

    fun isValid(): Boolean {
        return birthYear != null
                && issueYear != null
                && expirationYear != null
                && height != null
                && hairColor != null
                && eyeColor != null
                && passportId != null
    }

    fun isValid2(): Boolean {
        return birthYear != null
                && issueYear != null
                && expirationYear != null
                && height != null
                && hairColor != null
                && eyeColor != null
                && passportId != null
                && birthYear >= 1920 && birthYear <= 2002
                && issueYear >= 2010 && issueYear <= 2020
                && expirationYear >= 2020 && expirationYear <= 2030
                && validHeight()
                && validHairColor()
                && eyeColor in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
                && validPassportId()
    }

    private fun validHeight(): Boolean {
        if (height != null && height.length >= 3) {
            val value = height.substring(0, height.lastIndex - 1).toInt()
            val unit = height.substring(height.lastIndex - 1, height.lastIndex + 1)

            if (unit == "cm" && value >= 150 && value <= 193) {
                return true
            }
            if (unit == "in" && value >= 59 && value <= 76) {
                return true
            }
        }
        return false
    }

    private fun validHairColor(): Boolean {
        if (hairColor != null) {
            return hairColor[0] == '#'
                    && hairColor.length == 7
                    && hairColor.toCharArray(1).all { it.isDigit() || it in listOf('a','b','c','d','e','f') }
        }
        return false
    }

    private fun validPassportId(): Boolean {
        if (passportId != null) {
            return passportId.length == 9
                    && passportId.toCharArray().all { it.isDigit() }
        }
        return false
    }

    companion object {
        fun from(input: String): Passport {
            val fields = input
                    .split(" ")
                    .map { val pair = it.split(":"); pair.first() to pair.last() }

            return Passport(
                    birthYear = fields.findLast { it.first == "byr" }?.second?.toInt(),
                    issueYear = fields.findLast { it.first == "iyr" }?.second?.toInt(),
                    expirationYear = fields.findLast { it.first == "eyr" }?.second?.toInt(),
                    height = fields.findLast { it.first == "hgt" }?.second,
                    hairColor = fields.findLast { it.first == "hcl" }?.second,
                    eyeColor = fields.findLast { it.first == "ecl" }?.second,
                    passportId = fields.findLast { it.first == "pid" }?.second,
                    countryId = fields.findLast { it.first == "cid" }?.second,
            )
        }
    }
}