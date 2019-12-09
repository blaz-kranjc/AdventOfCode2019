package day5

import util.Program
import util.sequence

fun main() {
    val inp = object {}.javaClass.getResource("/day5/input.txt").readText().trim()
    val instructions = inp.split(',').map { it.toLongOrNull() }.sequence() ?: return
    val program = Program(instructions.toList())
    println(program.rerun(listOf(1)).second.first { it != 0L })
    println(program.rerun(listOf(5)).second.first { it != 0L })
}
