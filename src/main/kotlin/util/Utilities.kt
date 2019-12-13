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

fun <T, U> Iterable<T>.product(other: Iterable<U>): Iterable<Pair<T, U>> =
    flatMap { a -> other.map { b -> Pair(a, b) } }

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

tailrec fun gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)
tailrec fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

fun lcm(a: Int, b: Int): Int = a * b / gcd(a, b)
fun lcm(a: Long, b: Long): Long = a * b / gcd(a, b)

fun <T> generatePermutations(l: List<T>): List<List<T>> = when (l.size) {
    1 -> listOf(listOf(l.first()))
    else -> l.flatMap { e -> generatePermutations(l - e).map { it + e } }
}
