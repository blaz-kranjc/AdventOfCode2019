package day3

import util.Direction
import util.IntVec2
import util.sequence

data class Segment(val direction: Direction, val stepSize: Int)

fun parseSegment(str: String): Segment? {
    val stepSize = str.substring(1).toIntOrNull() ?: return null
    val direction = Direction.fromChar(str.first()) ?: return null
    return Segment(direction, stepSize)
}

internal fun toPoints(segment: Segment, start: IntVec2): List<IntVec2> =
    when (segment.stepSize) {
        0 -> emptyList()
        else -> (1..segment.stepSize).map {
            when (segment.direction) {
                Direction.Down -> IntVec2(start.x, start.y - it)
                Direction.Up -> IntVec2(start.x, start.y + it)
                Direction.Left -> IntVec2(start.x - it, start.y)
                Direction.Right -> IntVec2(start.x + it, start.y)
            }
        }
    }

internal fun toPoints(segments: Iterable<Segment>, origin: IntVec2 = IntVec2(0, 0)): List<IntVec2> =
    segments
        .fold(Pair(listOf(origin), origin)) { (l, p), segment ->
            val newList = l + toPoints(segment, p)
            Pair(newList, newList.last())
        }
        .first

class WireProbe(firstWire: Iterable<Segment>, secondWire: Iterable<Segment>) {
    private val firstPoints: List<IntVec2> = toPoints(firstWire)
    private val secondPoints: List<IntVec2> = toPoints(secondWire)
    private val intersections: List<IntVec2>?

    init {
        intersections = firstPoints.intersect(secondPoints).filterNot { it == IntVec2(0, 0) }
    }

    fun closestIntersectionManhattan(): Int? =
        intersections?.map { it.lengthManhattan() }?.min()

    fun closestIntersectionWire(): Int? =
        wireDistanceToIntersection(firstPoints)
            ?.mapNotNull { (p, cnt) -> wireDistanceToIntersection(secondPoints)?.get(p)?.plus(cnt) }
            ?.min()

    private fun wireDistanceToIntersection(points: List<IntVec2>): Map<IntVec2, Int>? {
        intersections ?: return null
        return points.withIndex()
            .filter { (_, point) -> point in intersections }
            .associate { (ind, point) -> point to ind }
    }
}

fun main() {
    val inp = object {}.javaClass.getResource("/day3/input.txt").readText().trim()
    val (wireA, wireB) = inp
        .split('\n')
        .map {
            it.split(',')
                .map { el -> parseSegment(el) }
                .sequence()
        }
        .sequence()
        ?.toList()
        ?: return

    val probe = WireProbe(wireA, wireB)
    println(probe.closestIntersectionManhattan())
    println(probe.closestIntersectionWire())
}
