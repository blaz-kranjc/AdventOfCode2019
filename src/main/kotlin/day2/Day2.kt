package day2

data class Program(val instructions: List<Int>)

fun run(ins: MutableList<Int>, current: Int = 0): List<Int>?  = when (ins[current]) {
    1 -> {
        ins[ins[current + 3]] = ins[ins[current + 1]] + ins[ins[current + 2]]
        run(ins, current + 4)
    }
    2 -> {
        ins[ins[current + 3]] = ins[ins[current + 1]] * ins[ins[current + 2]]
        run(ins, current + 4)
    }
    99 -> ins
    else -> null
}

fun Program.run(noun: Int, verb: Int): Int? {
    val instr = instructions.toMutableList()
    instr[1] = noun
    instr[2] = verb
    return run(instr)?.get(0)
}

fun <T, U> Sequence<T>.product(other: Sequence<U>): Sequence<Pair<T, U>> =
    flatMap { a -> other.map { b ->  Pair(a, b) }}

fun main() {
    val inp = object {}.javaClass.getResource("/day2/input.txt").readText().trim()
    val program = Program(inp.split(',').map { it.toInt() }.toList())
    println(program.run(12, 2))

    val possibleValues = (0..99).asSequence()
    possibleValues
        .product(possibleValues)
        .find { (n, v) -> program.run(n, v) == 19690720 }
        ?.also { (n, v) -> println(100 * n + v) }
}
