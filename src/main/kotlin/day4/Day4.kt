package day4

import util.toDigits
import kotlin.math.pow

fun <T> Collection<T>.consecutiveGroups(): List<Int> = when {
    isEmpty() -> emptyList()
    else -> drop(1).fold(Pair(listOf(1), this.elementAt(0))) { (l, current), t ->
        when (t) {
            current -> Pair(l.dropLast(1) + (l.last() + 1), current)
            else -> Pair(l + 1, t)
        }
    }.first
}

class IncreasingDigitsInt private constructor(val digits: List<Int>) {
    companion object {
        fun nextValid(num: Int): IncreasingDigitsInt {
            val digits = num.toDigits()
            return IncreasingDigitsInt(makeIncreasing(digits))
        }

        private fun makeIncreasing(digits: List<Int>): List<Int> =
            digits.drop(1).fold(Pair<Int?, List<Int>>(null, listOf(digits[0]))) { (prev, list), i ->
                when {
                    prev == null && list.last() <= i -> Pair(prev, list + i)
                    prev == null -> Pair(list.last(), list + list.last())
                    else -> Pair(prev, list + prev)
                }
            }.second
    }

    fun toInt(): Int =
        digits.reversed().withIndex().map { it.value * 10f.pow(it.index).toInt() }.sum()

    fun next(): IncreasingDigitsInt {
        val (carry, digits) = digits.foldRight(Pair(1, listOf<Int>())) { d, (carry, newDs) ->
            when {
                d == 9 && carry == 1 -> Pair(1, newDs + 0)
                else -> Pair(0, newDs + (d + carry))
            }
        }
        val newDigits = if (carry == 1) (digits + 1).reversed() else digits.reversed()
        return IncreasingDigitsInt(makeIncreasing(newDigits))
    }
}

fun increasingDigitsSeq(from: Int, to: Int): Sequence<IncreasingDigitsInt> =
    generateSequence(IncreasingDigitsInt.nextValid(from)) { it.next() }.takeWhile { it.toInt() <= to }


fun isValidFirst(n: IncreasingDigitsInt): Boolean {
    return n.digits.consecutiveGroups().any { it >= 2 }
}

fun isValidSecond(n: IncreasingDigitsInt): Boolean {
    return n.digits.consecutiveGroups().any { it == 2 }
}

fun main() {
    val (minValue, maxValue) = Pair(272091, 815432)
    println(increasingDigitsSeq(minValue, maxValue)
        .filter { isValidFirst(it) }
        .count())
    println(increasingDigitsSeq(minValue, maxValue)
        .filter { isValidSecond(it) }
        .count())
}
