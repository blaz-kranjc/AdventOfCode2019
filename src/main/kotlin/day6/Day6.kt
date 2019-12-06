package day6

import util.sequence

fun allDistances(
    orbits: Map<String, String>
): Map<String, Int> {
    val memo: MutableMap<String, Int> = mutableMapOf()
    fun distance(obj: String): Int = when {
        memo.containsKey(obj) -> memo[obj]!!
        orbits.containsKey(obj) -> {
            val dist = distance(orbits[obj]!!) + 1
            memo[obj] = dist
            dist
        }
        else -> 0
    }
    orbits.keys.forEach { distance(it) }
    return memo
}

fun countTransfersBetween(
    first: String,
    second: String,
    orbits: Map<String, String>
): Int {
    fun parentsOf(obj: String): Set<String> = when {
        orbits.containsKey(obj) -> parentsOf(orbits[obj]!!) + obj
        else -> emptySet()
    }

    val firstParents = parentsOf(first)
    val secondParents = parentsOf(second)
    val visited = firstParents.filterNot { secondParents.contains(it) }.count() +
            secondParents.filterNot { firstParents.contains(it) }.count()
    return visited - 2
}

fun main() {
    val inp = object {}.javaClass.getResource("/day6/input.txt").readText().trim()
    val orbits = inp.split('\n')
        .map {
            val objects = it.split(')')
            if (objects.size == 2) objects[1] to objects[0]
            else null
        }
        .sequence()
        ?.toMap()
        ?: return

    println(allDistances(orbits).map { it.value }.sum())
    println(countTransfersBetween("YOU", "SAN", orbits))
}
