package day12

import util.IntVec3
import util.lcm
import util.sequence
import kotlin.math.sign

data class Moon(val position: IntVec3, val velocity: IntVec3 = IntVec3(0, 0, 0))

fun energy(moon: Moon): Int =
    moon.position.lengthManhattan() * moon.velocity.lengthManhattan()

fun gravity(moon1: Moon, moon2: Moon): IntVec3 =
    IntVec3(
        (moon2.position.x - moon1.position.x).sign,
        (moon2.position.y - moon1.position.y).sign,
        (moon2.position.z - moon1.position.z).sign
    )

tailrec fun simulate(moons: List<Moon>, nSteps: Int): List<Moon> = when (nSteps) {
    0 -> moons
    else -> {
        val dvs = moons.map { m ->
            moons.fold(IntVec3(0, 0, 0)) { acc, moon ->
                acc + gravity(m, moon)
            }
        }
        val newMoons = moons.zip(dvs).map { (moon, dv) ->
            val newVelocity = moon.velocity + dv
            Moon(moon.position + newVelocity, newVelocity)
        }
        simulate(newMoons, nSteps - 1)
    }
}

fun period(moons: List<Moon>): Long {
    var current = moons
    var xPeriod: Long? = null
    var yPeriod: Long? = null
    var zPeriod: Long? = null
    var i = 0L
    while (xPeriod == null || yPeriod == null || zPeriod == null) {
        i += 1
        current = simulate(current, 1)
        if (xPeriod == null
            && current.map { it.position.x } == moons.map { it.position.x }
            && current.map { it.velocity.x } == moons.map { it.velocity.x }
        ) xPeriod = i
        if (yPeriod == null
            && current.map { it.position.y } == moons.map { it.position.y }
            && current.map { it.velocity.y } == moons.map { it.velocity.y }
        ) yPeriod = i
        if (zPeriod == null
            && current.map { it.position.z } == moons.map { it.position.z }
            && current.map { it.velocity.z } == moons.map { it.velocity.z }
        ) zPeriod = i
    }
    return lcm(lcm(zPeriod, yPeriod), xPeriod)
}

fun main() {
    val inp = object {}.javaClass.getResource("/day12/input.txt").readText().trim()
    val moons = inp.split('\n')
        .map {
            val m = """<x=(?<x>-?\d+), y=(?<y>-?\d+), z=(?<z>-?\d+)>"""
                .toRegex()
                .matchEntire(it)
            val x = m?.groups?.get("x")?.value?.toIntOrNull()
            val y = m?.groups?.get("y")?.value?.toIntOrNull()
            val z = m?.groups?.get("z")?.value?.toIntOrNull()
            if (x == null || y == null || z == null) null
            else Moon(IntVec3(x, y, z))
        }
        .sequence()
        ?.toList()
        ?: return
    val shortSimulation = simulate(moons, 1000)
    println(shortSimulation.sumBy { energy(it) })

    println(period(moons))
}
