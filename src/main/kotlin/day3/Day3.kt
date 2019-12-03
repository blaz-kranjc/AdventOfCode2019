package day3

import kotlin.math.*

enum class Direction { Up, Down, Left, Right }
fun parseDirection(dir: Char): Direction? = when (dir) {
    'U' -> Direction.Up
    'D' -> Direction.Down
    'L' -> Direction.Left
    'R' -> Direction.Right
    else -> null
}

data class Instruction(val direction: Direction, val stepSize: Int)
fun parseInstruction(instruction: String): Instruction? {
    val stepSize = instruction.substring(1).toIntOrNull()
    val direction = parseDirection(instruction.first())
    return if (stepSize != null && direction != null) Instruction(direction, stepSize) else null
}

data class Point(val x: Int, val y: Int) {
    fun manhattanDistance(other: Point = Point(0, 0)): Int = abs( this.x - other.x) + abs(this.y - other.y)
}

internal fun toPoints(instruction: Instruction, start: Point): List<Point> =
    when(instruction.stepSize) {
        0 -> emptyList()
        else -> (1..instruction.stepSize).map { when(instruction.direction) {
            Direction.Down -> Point(start.x, start.y - it)
            Direction.Up -> Point(start.x, start.y + it)
            Direction.Left -> Point(start.x - it, start.y)
            Direction.Right-> Point(start.x + it, start.y)
        }}
    }

internal fun toPointsAll(instructions: Iterable<Instruction>, origin: Point = Point(0, 0)): List<Point> =
    instructions
        .fold(Pair(listOf(origin), origin)) { (l, p), instruction ->
            val newList = l + toPoints(instruction, p)
            Pair(newList, newList.last())
        }
        .first

fun <T> Iterable<T?>.sequence(): Iterable<T>? =
    if (all { it != 0 }) map { it!! } else null

class WireProbe(firstWire: Iterable<Instruction>, secondWire: Iterable<Instruction>) {
    private val firstPoints: List<Point> = toPointsAll(firstWire)
    private val secondPoints: List<Point> = toPointsAll(secondWire)
    private val intersections: List<Point>?

    init {
        intersections = firstPoints.intersect(secondPoints).filterNot { it == Point(0, 0) }
    }

    fun closestIntersectionManhattan(): Int? =
        intersections?.map { it.manhattanDistance() }?.min()

    fun closestIntersectionWire(): Int? =
        wireDistanceToIntersection(firstPoints)
            ?.mapNotNull { (p, cnt) -> wireDistanceToIntersection(secondPoints)?.getValue(p)?.plus(cnt) }
            ?.min()

    private fun wireDistanceToIntersection(points: List<Point>): Map<Point, Int>? {
        intersections ?: return null
        return points.withIndex().filter { (_, point) -> point in intersections }.associate { (ind, point) -> point to ind }
    }
}

fun main() {
    val inp = object {}.javaClass.getResource("/day3/input.txt").readText().trim()
    val wires = inp
        .split('\n')
        .map { it
            .split(',')
            .map { el -> parseInstruction(el) }
            .sequence()
        }
        .sequence()

    if (wires == null || wires.count() != 2) return

    val (wireA, wireB) = wires.toList()
    val probe = WireProbe(wireA, wireB)
    println(probe.closestIntersectionManhattan())
    println(probe.closestIntersectionWire())
}
