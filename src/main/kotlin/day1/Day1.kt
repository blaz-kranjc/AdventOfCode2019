package day1

import util.sequence

fun fuelRequired(mass: Int): Int = (mass / 3) - 2
fun fuelRequiredFull(mass: Int): Int {
    val newFuel = fuelRequired(mass)
    return when {
        newFuel <= 0 -> 0
        else -> newFuel + fuelRequiredFull(newFuel)
    }
}

fun main() {
    val inp = object {}.javaClass.getResource("/day1/input.txt").readText().trim()
    val masses = inp.lines().map { it.toIntOrNull() }.sequence()
    println(masses?.sumBy { fuelRequired(it) })
    println(masses?.sumBy { fuelRequiredFull(it) })
}
