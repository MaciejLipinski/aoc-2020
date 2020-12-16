package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Ignore
import org.junit.Test

class Day16Test {

    @Test
    @Ignore
    fun testPartOne() {
        assertThat(Day16().partOne(), `is`(71))
    }

    @Test
    fun testPartTwo() {
        assertThat(Day16().partTwo(), `is`(143))
    }
}