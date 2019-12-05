package day5

import kotlin.test.Test
import kotlin.test.assertEquals

class Day5Test {
    @Test
    fun `Example echo program works`() {
        val echo = Program(listOf(3, 0, 4, 0, 99))
        assertEquals(echo.run(listOf(0)), listOf(0))
        assertEquals(echo.run(listOf(3)), listOf(3))
        assertEquals(echo.run(listOf(-1)), listOf(-1))
    }

    @Test
    fun `Example check for eight program (with position args) works`() {
        val isEight = Program(listOf(3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8))
        assertEquals(isEight.run(listOf(8)), listOf(1))
        assertEquals(isEight.run(listOf(0)), listOf(0))
        assertEquals(isEight.run(listOf(-1)), listOf(0))
    }

    @Test
    fun `Example check for eight program (with value args) works`() {
        val isEightPos = Program(listOf(3, 3, 1108, -1, 8, 3, 4, 3, 99))
        assertEquals(isEightPos.run(listOf(8)), listOf(1))
        assertEquals(isEightPos.run(listOf(0)), listOf(0))
        assertEquals(isEightPos.run(listOf(-1)), listOf(0))
    }

    @Test
    fun `Example check for less than eight program (with position args) works`() {
        val lessThanEight = Program(listOf(3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8))
        assertEquals(lessThanEight.run(listOf(8)), listOf(0))
        assertEquals(lessThanEight.run(listOf(9)), listOf(0))
        assertEquals(lessThanEight.run(listOf(-1)), listOf(1))
    }

    @Test
    fun `Example check for less than eight program (with value args) works`() {
        val lessThanEightPos = Program(listOf(3, 3, 1107, -1, 8, 3, 4, 3, 99))
        assertEquals(lessThanEightPos.run(listOf(8)), listOf(0))
        assertEquals(lessThanEightPos.run(listOf(9)), listOf(0))
        assertEquals(lessThanEightPos.run(listOf(-1)), listOf(1))
    }

    @Test
    fun `Example jump program (with position args) works`() {
        val lessThanEightPos = Program(listOf(3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9))
        assertEquals(lessThanEightPos.run(listOf(0)), listOf(0))
        assertEquals(lessThanEightPos.run(listOf(9)), listOf(1))
        assertEquals(lessThanEightPos.run(listOf(-1)), listOf(1))
    }

    @Test
    fun `Example jump program (with value args) works`() {
        val lessThanEightPos = Program(listOf(3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1))
        assertEquals(lessThanEightPos.run(listOf(0)), listOf(0))
        assertEquals(lessThanEightPos.run(listOf(9)), listOf(1))
        assertEquals(lessThanEightPos.run(listOf(-1)), listOf(1))
    }

    @Test
    fun `Example longer program works`() {
        val lessThanEightPos = Program(
            listOf(
                3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31,
                1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104,
                999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99
            )
        )
        assertEquals(lessThanEightPos.run(listOf(7)), listOf(999))
        assertEquals(lessThanEightPos.run(listOf(8)), listOf(1000))
        assertEquals(lessThanEightPos.run(listOf(9)), listOf(1001))
    }
}
