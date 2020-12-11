package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test

class Day11Test {

    @Test
    fun testPartOne() {
        assertThat(Day11().partOne(), `is`(37))
    }

    @Test
    fun testPartTwo() {
        assertThat(Day11().partTwo(), `is`(26))
    }
}