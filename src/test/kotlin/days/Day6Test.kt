package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test

class Day6Test {

    @Test
    fun testPartOne() {
        assertThat(Day6().partOne(), `is`(11))
    }

    @Test
    fun testPartTwo() {
        assertThat(Day6().partTwo(), `is`(6))
    }
}