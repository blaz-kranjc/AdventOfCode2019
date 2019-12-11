package day10

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

// TODO how do I do parametrized tests with kotlin test
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
        assertEquals(8, numberObservables(Point(3, 4), asteroids))
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
        assertEquals(33, numberObservables(Point(5, 8), asteroids))
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
        assertEquals(35, numberObservables(Point(1, 2), asteroids))
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
        assertEquals(41, numberObservables(Point(6, 3), asteroids))
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
        assertEquals(210, numberObservables(Point(11, 13), asteroids))

        val shootingOrder = shootingOrder(Point(11, 13), asteroids)
        assertEquals(shootingOrder[0], Point(11, 12))
        assertEquals(shootingOrder[1], Point(12, 1))
        assertEquals(shootingOrder[2], Point(12, 2))
        assertEquals(shootingOrder[9], Point(12, 8))
        assertEquals(shootingOrder[19], Point(16, 0))
        assertEquals(shootingOrder[49], Point(16, 9))
        assertEquals(shootingOrder[99], Point(10, 16))
        assertEquals(shootingOrder[198], Point(9, 6))
        assertEquals(shootingOrder[199], Point(8, 2))
        assertEquals(shootingOrder[200], Point(10, 9))
        assertEquals(shootingOrder[298], Point(11, 1))
    }
}