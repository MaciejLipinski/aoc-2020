package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Ignore
import org.junit.Test

class Day17Test {

    @Test
    fun testPartOne() {
        assertThat(Day17().partOne(), `is`(112))
    }

    @Test
    fun testPartTwo() {
        assertThat(Day17().partTwo(), `is`(848))
    }
}