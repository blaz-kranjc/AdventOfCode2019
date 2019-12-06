package util

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class UtilitiesTest {
    @Test
    fun `Sequence returns null if any is null`() {
        assertNull(listOf(1, 2, null).sequence())
        assertNull(listOf(null).sequence())
        assertNull(listOf(1, null).sequence())
    }

    @Test
    fun `Sequence returns same value if no nulls`() {
        assertEquals(listOf(1, 2, 3).sequence(), listOf(1, 2, 3))
        assertEquals(listOf<Int>().sequence(), listOf())
    }

    @Test
    fun `To digits correctly deconstructs numbers`() {
        assertEquals(0.toDigits(), listOf(0))
        assertEquals(123.toDigits(), listOf(1, 2, 3))
    }

    @Test
    fun `Product of two lists correct`() {
        assertEquals(
            sequenceOf(1, 2, 3).product(sequenceOf(4, 6)).toList(),
            listOf(1 to 4, 1 to 6, 2 to 4, 2 to 6, 3 to 4, 3 to 6)
        )
    }
}
