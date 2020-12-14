package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test

class Day14Test {

    @Test
    fun testPartOne() {
        assertThat(Day14().partOne(), `is`(51L))
    }

    @Test
    fun testPartTwo() {
        assertThat(Day14().partTwo(), `is`(208L))
    }
}