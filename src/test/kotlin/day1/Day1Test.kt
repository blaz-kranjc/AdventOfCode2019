package day1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day1Test {
    @Test
    fun `Provided examples for single step of fuel calculation return provided value`() {
        assertEquals(2, fuelRequired(12))
        assertEquals(2, fuelRequired(14))
        assertEquals(654, fuelRequired(1969))
        assertEquals(33583, fuelRequired(100756))
    }

    @Test
    fun `Provided examples for whole fuel calculation return provided value`() {
        assertEquals(2, fuelRequiredFull(14))
        assertEquals(966, fuelRequiredFull(1969))
        assertEquals(50346, fuelRequiredFull(100756))
    }
}
