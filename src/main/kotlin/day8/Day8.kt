package day8

import util.sequence

fun <T> List<T>.chunk(chunkSize: Int): List<List<T>>? {
    return if (size % chunkSize != 0) null
    else (0..size / chunkSize).zipWithNext().map { (from, to) -> subList(from * chunkSize, to * chunkSize) }
}

fun <T> List<List<T>>.transpose(): List<List<T>> = when {
    isEmpty() -> emptyList()
    else -> {
        val init = first().map { listOf(it) }
        drop(1).fold(init) { acc, list ->
            acc.zip(list).map { (l, el) -> l + el }
        }
    }
}

fun main() {
    val inp = object {}.javaClass.getResource("/day8/input.txt").readText().trim()
    val xSize = 25
    val ySize = 6
    val chunks = inp.toList().chunk(xSize * ySize)
    val layerWithMinZero = chunks
        ?.minBy { it.count { c -> c == '0' } }
        ?: return
    println(layerWithMinZero.count { it == '1' } * layerWithMinZero.count { it == '2' })

    val img = chunks.transpose()
        .map { it.firstOrNull { c -> c != '2' } ?: '2' }
        .map {
            when (it) {
                '1' -> '█'
                else -> ' '
            }
        }
        .chunk(xSize)
        ?.sequence()
    println(
        img?.joinToString(
            separator = "\n",
            transform = { it.joinToString(separator = "", prefix = "", postfix = "") })
    )
}
