package day4

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Day4Test {
    private fun assertAscending(list: List<Int>) {
        list.zipWithNext().forEach { (l, r) -> assertTrue(l <= r) }
    }

    @Test
    fun `Increasing digit construction provides an increasing digit only number`() {
        assertAscending(IncreasingDigitsInt.nextValid(432421).digits)
        assertAscending(IncreasingDigitsInt.nextValid(123214).digits)
        assertAscending(IncreasingDigitsInt.nextValid(89182).digits)
    }

    @Test
    fun `Increasing digit construction provides correct value`() {
        assertEquals(111111, IncreasingDigitsInt.nextValid(100000).toInt())
        assertEquals(123456, IncreasingDigitsInt.nextValid(123456).toInt())
        assertEquals(123333, IncreasingDigitsInt.nextValid(123193).toInt())
    }

    @Test
    fun `Increasing digit next method provides the next valid value`() {
        assertEquals(123457, IncreasingDigitsInt.nextValid(123456).next().toInt())
        assertEquals(133333, IncreasingDigitsInt.nextValid(129999).next().toInt())
    }

    @Test
    fun `Increasing digit sequence generates correct values`() {
        assertEquals(
            listOf(79, 88, 89, 99, 111),
            increasingDigitsSeq(79, 111).map { it.toInt() }.toList()
        )
        assertEquals(
            listOf(88, 89, 99),
            increasingDigitsSeq(87, 110).map { it.toInt() }.toList()
        )
    }

    @Test
    fun `Examples for passwords for first part work as expected`() {
        assertTrue(isValidFirst(IncreasingDigitsInt.nextValid(111111)))
        assertFalse(isValidFirst(IncreasingDigitsInt.nextValid(123789)))
    }

    @Test
    fun `Examples for passwords of second part work as expected`() {
        assertTrue(isValidSecond(IncreasingDigitsInt.nextValid(112233)))
        assertFalse(isValidSecond(IncreasingDigitsInt.nextValid(123444)))
        assertTrue(isValidSecond(IncreasingDigitsInt.nextValid(111122)))
    }
}
