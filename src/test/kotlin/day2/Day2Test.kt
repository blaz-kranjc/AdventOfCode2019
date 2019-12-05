package day2

import kotlin.test.Test
import kotlin.test.assertEquals

class Day2Test {
    @Test
    fun `Provided examples for run return provided value`() {
        assertEquals(listOf(2, 0, 0, 0, 99), run(mutableListOf(1, 0, 0, 0, 99)))
        assertEquals(listOf(2, 3, 0, 6, 99), run(mutableListOf(2, 3, 0, 3, 99)))
        assertEquals(listOf(2, 4, 4, 5, 99, 9801), run(mutableListOf(2, 4, 4, 5, 99, 0)))
        assertEquals(listOf(30, 1, 1, 4, 2, 5, 6, 0, 99), run(mutableListOf(1, 1, 1, 4, 99, 5, 6, 0, 99)))
    }
}
