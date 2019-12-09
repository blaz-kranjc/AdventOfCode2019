package util

import kotlin.test.Test
import kotlin.test.assertEquals

class ProgramTest {

    @Test
    fun `Example echo program works`() {
        val echo = Program(listOf(3, 0, 4, 0, 99).map { it.toLong() })
        assertEquals(echo.rerun(listOf(0)), Pair(StopType.Halted, listOf(0L)))
        assertEquals(echo.rerun(listOf(3)), Pair(StopType.Halted, listOf(3L)))
        assertEquals(echo.rerun(listOf(-1)), Pair(StopType.Halted, listOf(-1L)))
    }

    @Test
    fun `Example check for eight program (with position args) works`() {
        val isEight = Program(listOf(3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8).map { it.toLong() })
        assertEquals(isEight.rerun(listOf(8)), Pair(StopType.Halted, listOf(1L)))
        assertEquals(isEight.rerun(listOf(0)), Pair(StopType.Halted, listOf(0L)))
        assertEquals(isEight.rerun(listOf(-1)), Pair(StopType.Halted, listOf(0L)))
    }

    @Test
    fun `Example check for eight program (with value args) works`() {
        val isEightPos = Program(listOf(3, 3, 1108, -1, 8, 3, 4, 3, 99).map { it.toLong() })
        assertEquals(isEightPos.rerun(listOf(8)), Pair(StopType.Halted, listOf(1L)))
        assertEquals(isEightPos.rerun(listOf(0)), Pair(StopType.Halted, listOf(0L)))
        assertEquals(isEightPos.rerun(listOf(-1)), Pair(StopType.Halted, listOf(0L)))
    }

    @Test
    fun `Example check for less than eight program (with position args) works`() {
        val lessThanEight = Program(listOf(3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8).map { it.toLong() })
        assertEquals(lessThanEight.rerun(listOf(8)), Pair(StopType.Halted, listOf(0L)))
        assertEquals(lessThanEight.rerun(listOf(9)), Pair(StopType.Halted, listOf(0L)))
        assertEquals(lessThanEight.rerun(listOf(-1)), Pair(StopType.Halted, listOf(1L)))
    }

    @Test
    fun `Example check for less than eight program (with value args) works`() {
        val lessThanEightPos = Program(listOf(3, 3, 1107, -1, 8, 3, 4, 3, 99).map { it.toLong() })
        assertEquals(lessThanEightPos.rerun(listOf(8)), Pair(StopType.Halted, listOf(0L)))
        assertEquals(lessThanEightPos.rerun(listOf(9)), Pair(StopType.Halted, listOf(0L)))
        assertEquals(lessThanEightPos.rerun(listOf(-1)), Pair(StopType.Halted, listOf(1L)))
    }

    @Test
    fun `Example jump program (with position args) works`() {
        val lessThanEightPos = Program(listOf(3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9).map { it.toLong() })
        assertEquals(lessThanEightPos.rerun(listOf(0)), Pair(StopType.Halted, listOf(0L)))
        assertEquals(lessThanEightPos.rerun(listOf(9)), Pair(StopType.Halted, listOf(1L)))
        assertEquals(lessThanEightPos.rerun(listOf(-1)), Pair(StopType.Halted, listOf(1L)))
    }

    @Test
    fun `Example jump program (with value args) works`() {
        val lessThanEightPos = Program(listOf(3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1).map { it.toLong() })
        assertEquals(lessThanEightPos.rerun(listOf(0)), Pair(StopType.Halted, listOf(0L)))
        assertEquals(lessThanEightPos.rerun(listOf(9)), Pair(StopType.Halted, listOf(1L)))
        assertEquals(lessThanEightPos.rerun(listOf(-1)), Pair(StopType.Halted, listOf(1L)))
    }

    @Test
    fun `Example longer program works`() {
        val lessThanEightPos = Program(
            listOf(
                3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31,
                1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104,
                999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99
            )
            .map { it.toLong() }
        )
        assertEquals(lessThanEightPos.rerun(listOf(7)), Pair(StopType.Halted, listOf(999L)))
        assertEquals(lessThanEightPos.rerun(listOf(8)), Pair(StopType.Halted, listOf(1000L)))
        assertEquals(lessThanEightPos.rerun(listOf(9)), Pair(StopType.Halted, listOf(1001L)))
    }

    @Test
    fun `Example quine works`() {
        val ins = listOf(109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99).map { it.toLong() }
        assertEquals(Program(ins).rerun(), Pair(StopType.Halted, ins))
    }

    @Test
    fun `Big number tests work`() {
        val value = Program(listOf(1102,34915192,34915192,7,4,7,99,0)).rerun()
        assertEquals(value.first, StopType.Halted)
        assertEquals(value.second.size, 1)
        assertEquals(value.second.first().toString().length, 16)

        assertEquals(Program(listOf(104L,1125899906842624L,99L)).rerun(), Pair(StopType.Halted, listOf(1125899906842624L)))
    }
}
