package day7

import util.Program
import util.sequence

private fun generatePermutations(l: List<Int>): List<List<Int>> = when (l.size) {
    1 -> listOf(listOf(l.first()))
    else -> l.flatMap { e -> generatePermutations(l - e).map { it + e } }
}

class AmplifierSequence(private val program: Program) {
    private val memo: MutableMap<Pair<Int, Int>, Int> = mutableMapOf()

    private fun amplify(phases: List<Int>, input: Int = 0): Int =
        phases.fold(input) { acc, el ->
            // TODO unsafe first could be null
            memo.getOrPut(Pair(el, acc)) { program.rerun(listOf(el, acc)).second.first() }
        }

    fun bestPhaseSetting(): Pair<List<Int>, Int> =
        generatePermutations((0..4).toList())
            .map { it to amplify(it) }
            .maxBy { it.second }!!
}

class LoopBackAmplifierSequence(program: Program) {
    private val programs: List<Program> = (0..4).map { program.copy() }

    // TODO this is hideous
    private fun amplify(phases: List<Int>): Int {
        val outputs =
            programs.zip(phases).map { (program, phase) -> program.rerun(listOf(phase)).second }.toMutableList()
        // Input first amplification
        outputs[0] += programs[0].run(listOf(0)).second
        while (programs.any { it.status() != Program.ProgramStatus.Halt }) {
            programs.withIndex().map { (index, program) ->
                val inputs = outputs[(programs.size + index - 1) % programs.size]
                if (program.status() != Program.ProgramStatus.Halt && inputs.isNotEmpty()) {
                    outputs[index] = program.run(inputs).second
                    outputs[(programs.size + index - 1) % programs.size] = emptyList()
                }
            }
        }
        return outputs[programs.size - 1].first()
    }

    fun bestPhaseSetting(): Pair<List<Int>, Int> =
        generatePermutations((5..9).toList())
            .map { it to amplify(it) }
            .maxBy { it.second }!!
}

fun main() {
    val inp = object {}.javaClass.getResource("/day7/input.txt").readText().trim()
    val instructions = inp.split(',').map { it.toIntOrNull() }.sequence() ?: return
    val program = Program(instructions.toList())
    println(AmplifierSequence(program).bestPhaseSetting().second)
    println(LoopBackAmplifierSequence(program).bestPhaseSetting().second)
}
