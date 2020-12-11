package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test

class Day10Test {

    @Test
    fun testPartOne() {
        assertThat(Day10().partOne(), `is`(220))
    }

    @Test
    fun testPartTwo() {
        assertThat(Day10().partTwo(), `is`(19208.0))
    }
}