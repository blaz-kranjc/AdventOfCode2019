package util

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

data class IntVec2(val x: Int, val y: Int) {
    fun length(): Double = sqrt(x.toDouble().pow(2) + y.toDouble().pow(2))
    fun lengthManhattan(): Int = abs(x) + abs(y)
    operator fun plus(other: IntVec2): IntVec2 = IntVec2(x + other.x, y + other.y)
    operator fun minus(other: IntVec2): IntVec2 = IntVec2(x - other.x, y - other.y)
}
