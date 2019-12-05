package day3

import util.sequence
import kotlin.math.abs

enum class Direction { Up, Down, Left, Right }

fun parseDirection(dir: Char): Direction? = when (dir) {
    'U' -> Direction.Up
    'D' -> Direction.Down
    'L' -> Direction.Left
    'R' -> Direction.Right
    else -> null
}

data class Segment(val direction: Direction, val stepSize: Int)

fun parseSegment(str: String): Segment? {
    val stepSize = str.substring(1).toIntOrNull() ?: return null
    val direction = parseDirection(str.first()) ?: return null
    return Segment(direction, stepSize)
}

data class Point(val x: Int, val y: Int) {
    fun manhattanDistance(other: Point = Point(0, 0)): Int = abs(this.x - other.x) + abs(this.y - other.y)
}

internal fun toPoints(segment: Segment, start: Point): List<Point> =
    when (segment.stepSize) {
        0 -> emptyList()
        else -> (1..segment.stepSize).map {
            when (segment.direction) {
                Direction.Down -> Point(start.x, start.y - it)
                Direction.Up -> Point(start.x, start.y + it)
                Direction.Left -> Point(start.x - it, start.y)
                Direction.Right -> Point(start.x + it, start.y)
            }
        }
    }

internal fun toPoints(segments: Iterable<Segment>, origin: Point = Point(0, 0)): List<Point> =
    segments
        .fold(Pair(listOf(origin), origin)) { (l, p), segment ->
            val newList = l + toPoints(segment, p)
            Pair(newList, newList.last())
        }
        .first

class WireProbe(firstWire: Iterable<Segment>, secondWire: Iterable<Segment>) {
    private val firstPoints: List<Point> = toPoints(firstWire)
    private val secondPoints: List<Point> = toPoints(secondWire)
    private val intersections: List<Point>?

    init {
        intersections = firstPoints.intersect(secondPoints).filterNot { it == Point(0, 0) }
    }

    fun closestIntersectionManhattan(): Int? =
        intersections?.map { it.manhattanDistance() }?.min()

    fun closestIntersectionWire(): Int? =
        wireDistanceToIntersection(firstPoints)
            ?.mapNotNull { (p, cnt) -> wireDistanceToIntersection(secondPoints)?.get(p)?.plus(cnt) }
            ?.min()

    private fun wireDistanceToIntersection(points: List<Point>): Map<Point, Int>? {
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
