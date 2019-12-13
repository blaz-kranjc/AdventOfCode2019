package day12

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import util.IntVec3

class Day12Test {
    companion object {
        private val example1 = listOf(
            Moon(IntVec3(-1, 0, 2)),
            Moon(IntVec3(2, -10, -7)),
            Moon(IntVec3(4, -8, 8)),
            Moon(IntVec3(3, 5, -1))
        )

        private val example2 = listOf(
            Moon(IntVec3(-8, -10, 0)),
            Moon(IntVec3(5, 5, 10)),
            Moon(IntVec3(2, -7, 3)),
            Moon(IntVec3(9, -8, -3))
        )
    }

    @Test
    fun `Moon orbits are calculated correctly for first example`() {
        assertEquals(
            listOf(
                Moon(IntVec3(2, -1, 1), IntVec3(3, -1, -1)),
                Moon(IntVec3(3, -7, -4), IntVec3(1, 3, 3)),
                Moon(IntVec3(1, -7, 5), IntVec3(-3, 1, -3)),
                Moon(IntVec3(2, 2, 0), IntVec3(-1, -3, 1))
            ),
            simulate(example1, 1)
        )

        assertEquals(
            listOf(
                Moon(IntVec3(2, 1, -3), IntVec3(-3, -2, 1)),
                Moon(IntVec3(1, -8, 0), IntVec3(-1, 1, 3)),
                Moon(IntVec3(3, -6, 1), IntVec3(3, 2, -3)),
                Moon(IntVec3(2, 0, 4), IntVec3(1, -1, -1))
            ),
            simulate(example1, 10)
        )
    }

    @Test
    fun `Moon orbits are calculated correctly for second example`() {
        assertEquals(
            listOf(
                Moon(IntVec3(-9, -10, 1), IntVec3(-2, -2, -1)),
                Moon(IntVec3(4, 10, 9), IntVec3(-3, 7, -2)),
                Moon(IntVec3(8, -10, -3), IntVec3(5, -1, -2)),
                Moon(IntVec3(5, -10, 3), IntVec3(0, -4, 5))
            ),
            simulate(example2, 10)
        )

        assertEquals(
            listOf(
                Moon(IntVec3(8, -12, -9), IntVec3(-7, 3, 0)),
                Moon(IntVec3(13, 16, -3), IntVec3(3, -11, -5)),
                Moon(IntVec3(-29, -11, -1), IntVec3(-3, 7, 4)),
                Moon(IntVec3(16, -13, 23), IntVec3(7, 1, 1))
            ),
            simulate(example2, 100)
        )
    }

    @Test
    fun `Energy calculation works correctly`() {
        assertEquals(36, energy(Moon(IntVec3(2, 1, -3), IntVec3(-3, -2, 1))))
        assertEquals(45, energy(Moon(IntVec3(1, -8, 0), IntVec3(-1, 1, 3))))
        assertEquals(80, energy(Moon(IntVec3(3, -6, 1), IntVec3(3, 2, -3))))
        assertEquals(18, energy(Moon(IntVec3(2, 0, 4), IntVec3(1, -1, -1))))

        assertEquals(290, energy(Moon(IntVec3(8, -12, -9), IntVec3(-7, 3, 0))))
        assertEquals(608, energy(Moon(IntVec3(13, 16, -3), IntVec3(3, -11, -5))))
        assertEquals(574, energy(Moon(IntVec3(-29, -11, -1), IntVec3(-3, 7, 4))))
        assertEquals(468, energy(Moon(IntVec3(16, -13, 23), IntVec3(7, 1, 1))))
    }

    @Test
    fun `Period calculations work correctly`() {
        assertEquals(2772L, period(example1))
        assertEquals(4686774924L, period(example2))
    }
}