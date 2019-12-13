package day10

import util.IntVec2
import util.gcd
import kotlin.math.sign

fun parseAsteroids(image: String): Set<IntVec2> =
    image.split("\n")
        .withIndex()
        .flatMap { (rowIndex, row) ->
            row.toCharArray()
                .withIndex()
                .mapNotNull { (colIndex, char) ->
                    if (char == '#') IntVec2(colIndex, rowIndex) else null
                }
        }.toSet()

fun groupByDirection(point: IntVec2, points: Set<IntVec2>): Collection<List<IntVec2>> =
    points.groupBy { (point - it).direction() }
        .filterNot { (k, _) -> k == IntVec2(0, 0) }
        .values

fun numberObservables(point: IntVec2, points: Set<IntVec2>): Int =
    groupByDirection(point, points).count()

fun bestObservationPoint(asteroids: Set<IntVec2>): Pair<IntVec2, Int>? =
    asteroids
        .map { asteroid -> asteroid to numberObservables(asteroid, asteroids) }
        .maxBy { (_, count) -> count }

fun shootingOrder(point: IntVec2, points: Set<IntVec2>): List<IntVec2> {
    val groups = groupByDirection(point, points)
    val circular = groups
        .sortedBy { v ->
            val vec = point - v.first()
            if (vec.x > 0) vec.y / vec.length() else -2 - vec.y / vec.length()
        }
        .map { group -> group.sortedBy { (it - point).lengthManhattan() }.toMutableList() }
        .toMutableList()
    val order = mutableListOf<IntVec2>()
    while (circular.isNotEmpty()) {
        circular.forEach { v ->
            order.add(v.first())
            v.removeAt(0)
        }
        circular.removeIf { it.isEmpty() }
    }
    return order
}

fun IntVec2.direction(): IntVec2 = when {
    x == 0 && y == 0 -> IntVec2(0, 0)
    x == 0 -> IntVec2(0, y.sign)
    y == 0 -> IntVec2(x.sign, 0)
    else -> {
        val denominator = x.sign * gcd(x, y)
        IntVec2(x / denominator, y / denominator)
    }
}

fun main() {
    val inp = object {}.javaClass.getResource("/day10/input.txt").readText().trim()
    val asteroids = parseAsteroids(inp)
    val bestAsteroid = bestObservationPoint(asteroids)
    bestAsteroid ?: return
    println(bestAsteroid.second)
    val twoHundredthKilled = shootingOrder(bestAsteroid.first, asteroids)[199]
    println(twoHundredthKilled.x * 100 + twoHundredthKilled.y)
}
