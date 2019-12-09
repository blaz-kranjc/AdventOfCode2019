package day2

import util.Program
import util.product
import util.sequence

fun runWith(instructions: List<Long>, noun: Int, verb: Int): Long {
    val prog = Program(instructions)
    prog.setMemory(1, noun.toLong())
    prog.setMemory(2, verb.toLong())
    prog.run()
    return prog.getMemory(0)
}

fun main() {
    val inp = object {}.javaClass.getResource("/day2/input.txt").readText().trim()
    val instructions = inp.split(',').map { it.toLongOrNull() }.sequence()?.toList() ?: return
    println(runWith(instructions, 12, 2))

    val possibleValues = (0..99).asSequence()
    val (n, v) = possibleValues
        .product(possibleValues)
        .find { (n, v) -> runWith(instructions, n, v) == 19690720L }
        ?: return
    println(100 * n + v)
}
