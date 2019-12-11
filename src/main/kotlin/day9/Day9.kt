package day9

import util.Program
import util.sequence

fun main() {
    val inp = object {}.javaClass.getResource("/day9/input.txt").readText().trim()
    val instructions = inp.split(',').map { it.toLongOrNull() }.sequence() ?: return
    val program = Program(instructions.toList())
    println(program.rerun(listOf(1)).second.first())
    println(program.rerun(listOf(2)).second.first())
}
