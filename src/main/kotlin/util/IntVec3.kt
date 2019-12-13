package util

import kotlin.math.abs

data class IntVec3(val x: Int, val y: Int, val z: Int) {
    fun lengthManhattan(): Int = abs(x) + abs(y) + abs(z)
    operator fun plus(other: IntVec3): IntVec3 =
        IntVec3(x + other.x, y + other.y, z + other.z)

    operator fun minus(other: IntVec3): IntVec3 =
        IntVec3(x - other.x, y - other.y, z - other.z)
}
