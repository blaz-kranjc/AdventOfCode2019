package day4

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Day4Test {
    @Test
    fun `Examples for passwords of first part work as expected`() {
        assertTrue { isValidFirst(111111) }
        assertFalse { isValidFirst(223450) }
        assertFalse { isValidFirst(123789) }
    }

    @Test
    fun `Examples for passwords of second part work as expected`() {
        assertTrue { isValidSecond(112233) }
        assertFalse { isValidSecond(123444) }
        assertTrue { isValidSecond(111122) }
        assertFalse { isValidSecond(223450) }
        assertFalse { isValidSecond(123789) }
    }
}
