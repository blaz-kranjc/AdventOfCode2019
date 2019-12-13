package day10

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import util.IntVec2

class Day10Test {
    @Test
    fun `Provided example 1 works correctly`() {
        val asteroids = parseAsteroids(
            ".#..#\n" +
                    ".....\n" +
                    "#####\n" +
                    "....#\n" +
                    "...##"
        )
        val (_, value) = bestObservationPoint(asteroids) ?: fail()
        assertEquals(8, value)
        assertEquals(8, numberObservables(IntVec2(3, 4), asteroids))
    }

    @Test
    fun `Provided example 2 works correctly`() {
        val asteroids = parseAsteroids(
            "......#.#.\n" +
                    "#..#.#....\n" +
                    "..#######.\n" +
                    ".#.#.###..\n" +
                    ".#..#.....\n" +
                    "..#....#.#\n" +
                    "#..#....#.\n" +
                    ".##.#..###\n" +
                    "##...#..#.\n" +
                    ".#....####"
        )
        val (_, value) = bestObservationPoint(asteroids) ?: fail()
        assertEquals(33, value)
        assertEquals(33, numberObservables(IntVec2(5, 8), asteroids))
    }

    @Test
    fun `Provided example 3 works correctly`() {
        val asteroids = parseAsteroids(
            "#.#...#.#.\n" +
                    ".###....#.\n" +
                    ".#....#...\n" +
                    "##.#.#.#.#\n" +
                    "....#.#.#.\n" +
                    ".##..###.#\n" +
                    "..#...##..\n" +
                    "..##....##\n" +
                    "......#...\n" +
                    ".####.###."
        )
        val (_, value) = bestObservationPoint(asteroids) ?: fail()
        assertEquals(35, value)
        assertEquals(35, numberObservables(IntVec2(1, 2), asteroids))
    }

    @Test
    fun `Provided example 4 works correctly`() {
        val asteroids = parseAsteroids(
            ".#..#..###\n" +
                    "####.###.#\n" +
                    "....###.#.\n" +
                    "..###.##.#\n" +
                    "##.##.#.#.\n" +
                    "....###..#\n" +
                    "..#.#..#.#\n" +
                    "#..#.#.###\n" +
                    ".##...##.#\n" +
                    ".....#.#.."
        )
        val (_, value) = bestObservationPoint(asteroids) ?: fail()
        assertEquals(41, value)
        assertEquals(41, numberObservables(IntVec2(6, 3), asteroids))
    }

    @Test
    fun `Provided example 5 works correctly`() {
        val asteroids = parseAsteroids(
            ".#..##.###...#######\n" +
                    "##.############..##.\n" +
                    ".#.######.########.#\n" +
                    ".###.#######.####.#.\n" +
                    "#####.##.#.##.###.##\n" +
                    "..#####..#.#########\n" +
                    "####################\n" +
                    "#.####....###.#.#.##\n" +
                    "##.#################\n" +
                    "#####.##.###..####..\n" +
                    "..######..##.#######\n" +
                    "####.##.####...##..#\n" +
                    ".#####..#.######.###\n" +
                    "##...#.##########...\n" +
                    "#.##########.#######\n" +
                    ".####.#.###.###.#.##\n" +
                    "....##.##.###..#####\n" +
                    ".#.#.###########.###\n" +
                    "#.#.#.#####.####.###\n" +
                    "###.##.####.##.#..##"
        )
        val (_, value) = bestObservationPoint(asteroids) ?: fail()
        assertEquals(210, value)
        assertEquals(210, numberObservables(IntVec2(11, 13), asteroids))
    }

    @Test
    fun `Provided example for shooting order works correctly`() {
        val asteroids = parseAsteroids(
            ".#..##.###...#######\n" +
                    "##.############..##.\n" +
                    ".#.######.########.#\n" +
                    ".###.#######.####.#.\n" +
                    "#####.##.#.##.###.##\n" +
                    "..#####..#.#########\n" +
                    "####################\n" +
                    "#.####....###.#.#.##\n" +
                    "##.#################\n" +
                    "#####.##.###..####..\n" +
                    "..######..##.#######\n" +
                    "####.##.####...##..#\n" +
                    ".#####..#.######.###\n" +
                    "##...#.##########...\n" +
                    "#.##########.#######\n" +
                    ".####.#.###.###.#.##\n" +
                    "....##.##.###..#####\n" +
                    ".#.#.###########.###\n" +
                    "#.#.#.#####.####.###\n" +
                    "###.##.####.##.#..##"
        )
        val shootingOrder = shootingOrder(IntVec2(11, 13), asteroids)
        assertEquals(shootingOrder[0], IntVec2(11, 12))
        assertEquals(shootingOrder[1], IntVec2(12, 1))
        assertEquals(shootingOrder[2], IntVec2(12, 2))
        assertEquals(shootingOrder[9], IntVec2(12, 8))
        assertEquals(shootingOrder[19], IntVec2(16, 0))
        assertEquals(shootingOrder[49], IntVec2(16, 9))
        assertEquals(shootingOrder[99], IntVec2(10, 16))
        assertEquals(shootingOrder[198], IntVec2(9, 6))
        assertEquals(shootingOrder[199], IntVec2(8, 2))
        assertEquals(shootingOrder[200], IntVec2(10, 9))
        assertEquals(shootingOrder[298], IntVec2(11, 1))
    }
}