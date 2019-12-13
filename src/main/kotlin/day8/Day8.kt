package day8

import util.chunk
import util.sequence
import util.transpose

private const val X_SIZE = 25
private const val Y_SIZE = 6

fun main() {
    val inp = object {}.javaClass.getResource("/day8/input.txt").readText().trim()
    val chunks = inp.toList().chunk(X_SIZE * Y_SIZE)
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
        .chunk(X_SIZE)
        ?.sequence()
    println(
        img?.joinToString(
            separator = "\n",
            transform = { it.joinToString(separator = "", prefix = "", postfix = "") })
    )
}
