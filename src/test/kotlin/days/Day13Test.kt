package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test

class Day13Test {

    @Test
    fun testPartOne() {
        assertThat(Day13().partOne(), `is`(295))
    }

    @Test
    fun testPartTwo() {
        assertThat(Day13().partTwo(), `is`(1068781L))
    }
}