package day2

import util.product
import util.sequence

data class Program(val instructions: List<Int>)

fun run(ins: MutableList<Int>, current: Int = 0): List<Int>? = when (ins[current]) {
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

fun main() {
    val inp = object {}.javaClass.getResource("/day2/input.txt").readText().trim()
    val instructions = inp.split(',').map { it.toIntOrNull() }.sequence() ?: return
    val program = Program(instructions.toList())
    println(program.run(12, 2))

    val possibleValues = (0..99).asSequence()
    val (n, v) = possibleValues
        .product(possibleValues)
        .find { (n, v) -> program.run(n, v) == 19690720 }
        ?: return
    println(100 * n + v)
}
