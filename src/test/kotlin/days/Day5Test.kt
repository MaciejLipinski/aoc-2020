package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test

class Day5Test {

    @Test
    fun testBoardingPass() {
        val boardingPass = BoardingPass("BFFFBBFRRR")
        assertThat(7, `is`(boardingPass.getColumn()))
        assertThat(70, `is`(boardingPass.getRow()))
        assertThat(567, `is`(boardingPass.getSeatId()))
    }
}