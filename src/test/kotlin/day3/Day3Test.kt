package day3

import kotlin.test.Test
import kotlin.test.assertEquals

class Day3Test {
    @Test
    fun `Instruction parsing works`() {
        assertEquals(u(123), parseSegment("U123"))
        assertEquals(d(1), parseSegment("D1"))
        assertEquals(l(72), parseSegment("L72"))
        assertEquals(r(15), parseSegment("R15"))
        assertEquals(null, parseSegment("U"))
        assertEquals(null, parseSegment("X123"))
        assertEquals(null, parseSegment("A1"))
    }

    @Test
    fun `First example for closest intersections by manhattan distance returns provided value`() {
        val wp = WireProbe(listOf(r(8), u(5), l(5), d(3)), listOf(u(7), r(6), d(4), l(4)))
        assertEquals(6, wp.closestIntersectionManhattan())
    }

    @Test
    fun `Second example for closest intersections by manhattan distance returns provided value`() {
        val example = WireProbe(
            listOf(r(75), d(30), r(83), u(83), l(12), d(49), r(71), u(7), l(72)),
            listOf(u(62), r(66), u(55), r(34), d(71), r(55), d(58), r(83))
        )
        assertEquals(159, example.closestIntersectionManhattan())
    }

    @Test
    fun `Third example for closest intersections by manhattan distance returns provided value`() {
        val example = WireProbe(
            listOf(r(98), u(47), r(26), d(63), r(33), u(87), l(62), d(20), r(33), u(53), r(51)),
            listOf(u(98), r(91), d(20), r(16), d(67), r(40), u(7), r(15), u(6), r(7))
        )
        assertEquals(135, example.closestIntersectionManhattan())
    }

    @Test
    fun `First example for closest intersections by wire returns provided value`() {
        val wp = WireProbe(listOf(r(8), u(5), l(5), d(3)), listOf(u(7), r(6), d(4), l(4)))
        assertEquals(30, wp.closestIntersectionWire())
    }

    @Test
    fun `Second example for closest intersections by wire returns provided value`() {
        val example = WireProbe(
            listOf(r(75), d(30), r(83), u(83), l(12), d(49), r(71), u(7), l(72)),
            listOf(u(62), r(66), u(55), r(34), d(71), r(55), d(58), r(83))
        )
        assertEquals(610, example.closestIntersectionWire())
    }

    @Test
    fun `Third example for closest intersections by wire returns provided value`() {
        val example = WireProbe(
            listOf(r(98), u(47), r(26), d(63), r(33), u(87), l(62), d(20), r(33), u(53), r(51)),
            listOf(u(98), r(91), d(20), r(16), d(67), r(40), u(7), r(15), u(6), r(7))
        )
        assertEquals(410, example.closestIntersectionWire())
    }

    private fun d(stepSize: Int): Segment = Segment(Direction.Down, stepSize)
    private fun u(stepSize: Int): Segment = Segment(Direction.Up, stepSize)
    private fun l(stepSize: Int): Segment = Segment(Direction.Left, stepSize)
    private fun r(stepSize: Int): Segment = Segment(Direction.Right, stepSize)

}
