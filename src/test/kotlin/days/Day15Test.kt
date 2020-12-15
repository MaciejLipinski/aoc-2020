package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test

class Day15Test {

    @Test
    fun testPartOne() {
        assertThat(Day15().partOne(), `is`(436L))
    }

    @Test
    fun testPartTwo() {
        assertThat(Day15().partTwo(), `is`(175594L))
    }
}