package day4

import util.toDigits

fun <T> Collection<T>.consecutiveCounts(): List<Int> = when {
    isEmpty() -> emptyList()
    else -> drop(1).fold(Pair(listOf(1), this.elementAt(0))) { (l, current), t ->
        when (t) {
            current -> Pair(l.dropLast(1) + (l.last() + 1), current)
            else -> Pair(l + 1, t)
        }
    }.first
}

fun isValidFirst(n: Int): Boolean {
    val digits = n.toDigits()
    return digits.zipWithNext().all { (a, b) -> a <= b } && (digits.consecutiveCounts().any { it >= 2 })
}

fun isValidSecond(n: Int): Boolean {
    val digits = n.toDigits()
    return digits.zipWithNext().all { (a, b) -> a <= b } && digits.consecutiveCounts().any { it == 2 }
}

fun main() {
    val (minValue, maxValue) = Pair(272091, 815432)
    // TODO generate "increasing digits" only integers
    println((minValue..maxValue).asSequence().filter { isValidFirst(it) }.count())
    println((minValue..maxValue).asSequence().filter { isValidSecond(it) }.count())
}
