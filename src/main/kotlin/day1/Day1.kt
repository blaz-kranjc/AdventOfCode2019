package day1

fun fuelRequired(mass: Int): Int = (mass / 3) - 2
fun fuelRequiredFull(mass: Int): Int {
    val newFuel = fuelRequired(mass)
    return when {
        newFuel <= 0 -> 0
        else -> newFuel + fuelRequiredFull(newFuel)
    }
}

fun main() {
    val inp = object {}.javaClass.getResource("/day1/input.txt").readText()
    val masses = inp.lines().mapNotNull { it.toIntOrNull() }
    println(masses.sumBy { fuelRequired(it) })
    println(masses.sumBy { fuelRequiredFull(it) })
}
