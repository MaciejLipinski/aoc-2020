package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test

class Day8Test {

    @Test
    fun testPartOne() {
        assertThat(Day8().partOne(), `is`(5))
    }

    @Test
    fun testPartTwo() {
        assertThat(Day8().partTwo(), `is`(8))
    }
}