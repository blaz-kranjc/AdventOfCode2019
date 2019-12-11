package day10

import kotlin.math.pow
import kotlin.math.sign
import kotlin.math.sqrt

fun parseAsteroids(image: String): Set<Point> =
    image.split("\n")
        .withIndex()
        .flatMap { (rowIndex, row) ->
            row.toCharArray()
                .withIndex()
                .mapNotNull { (colIndex, char) ->
                    if (char == '#') Point(colIndex, rowIndex) else null
                }
        }.toSet()

tailrec fun gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

fun groupByDirection(point: Point, points: Set<Point>): Collection<List<Point>> =
    points.groupBy { (point - it).direction() }
        .filterNot { (k, _) -> k == Point(0, 0) }
        .values

fun numberObservables(point: Point, points: Set<Point>): Int =
    groupByDirection(point, points).count()

fun bestObservationPoint(asteroids: Set<Point>): Pair<Point, Int>? =
    asteroids
        .map { asteroid -> asteroid to numberObservables(asteroid, asteroids) }
        .maxBy { (_, count) -> count }

fun shootingOrder(point: Point, points: Set<Point>): List<Point> {
    val groups = groupByDirection(point, points)
    val circular = groups.sortedBy { v ->
        val vec = point - v.first()
        if (vec.x > 0) vec.y / vec.length() else -2 - vec.y / vec.length()
    }
        .map { group -> group.sortedBy { (it - point).length() }.toMutableList() }
        .toMutableList()
    val order = mutableListOf<Point>()
    while (circular.isNotEmpty()) {
        circular.forEach { v ->
            order.add(v.first())
            v.removeAt(0)
        }
        circular.removeIf { it.isEmpty() }
    }
    return order
}

data class Point(val x: Int, val y: Int) {
    fun direction(): Point =
        when {
            x == 0 && y == 0 -> Point(0, 0)
            x == 0 -> Point(0, y.sign)
            y == 0 -> Point(x.sign, 0)
            else -> {
                val denominator = x.sign * gcd(x, y)
                Point(x / denominator, y / denominator)
            }
        }

    fun length(): Double = sqrt(x.toDouble().pow(2) + y.toDouble().pow(2))

    operator fun plus(other: Point): Point = Point(x + other.x, y + other.y)
    operator fun minus(other: Point): Point = Point(x - other.x, y - other.y)
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
