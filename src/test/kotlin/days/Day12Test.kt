package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test

class Day12Test {

    @Test
    fun testPartOne() {
        assertThat(Day12().partOne(), `is`(25))
    }

    @Test
    fun testPartTwo() {
        assertThat(Day12().partTwo(), `is`(286))
    }
}