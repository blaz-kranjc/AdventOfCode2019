package day6

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day6Test {
    @Test
    fun `Example for number of orbits works`() {
        val orbits = mapOf(
            "B" to "COM",
            "C" to "B",
            "D" to "C",
            "E" to "D",
            "F" to "E",
            "G" to "B",
            "H" to "G",
            "I" to "D",
            "J" to "E",
            "K" to "J",
            "L" to "K"
        )
        assertEquals(allDistances(orbits).map { it.value }.sum(), 42)
    }

    @Test
    fun `Example for distance between nodes works`() {
        val orbits = mapOf(
            "B" to "COM",
            "C" to "B",
            "D" to "C",
            "E" to "D",
            "F" to "E",
            "G" to "B",
            "H" to "G",
            "I" to "D",
            "J" to "E",
            "K" to "J",
            "L" to "K",
            "YOU" to "K",
            "SAN" to "I"
        )
        assertEquals(countTransfersBetween("YOU", "SAN", orbits), 4)
    }
}
