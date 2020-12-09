package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test

class Day7Test {

    @Test
    fun testPartOne() {
        assertThat(Day7().partOne(), `is`(4))
    }

    @Test
    fun testPartTwo() {
        assertThat(Day7().partTwo(), `is`(32))
    }
}