package util

fun Int.toDigits(acc: List<Int> = emptyList()): List<Int> = when {
    this < 0 -> acc
    this in 0..9 -> (acc + this).reversed()
    else -> (this / 10).toDigits(acc + this % 10)
}

fun Long.toDigits(acc: List<Int> = emptyList()): List<Int> = when {
    this < 0 -> acc
    this in 0..9 -> (acc + this.toInt()).reversed()
    else -> (this / 10).toDigits(acc + (this % 10).toInt())
}

fun <T> Iterable<T?>.sequence(): Iterable<T>? = if (all { it != null }) map { it!! } else null

fun <T, U> Sequence<T>.product(other: Sequence<U>): Sequence<Pair<T, U>> =
    flatMap { a -> other.map { b -> Pair(a, b) } }
