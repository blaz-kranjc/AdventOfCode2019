package day7

import util.Program
import util.generatePermutations
import util.sequence

class AmplifierSequence(private val program: Program) {
    private val memo: MutableMap<Pair<Int, Long>, Long> = mutableMapOf()

    private fun amplify(phases: List<Int>, input: Long = 0): Long =
        phases.fold(input) { acc, el ->
            // TODO unsafe first could be null
            memo.getOrPut(Pair(el, acc)) { program.rerun(listOf(el.toLong(), acc)).second.first() }
        }

    fun bestPhaseSetting(): Pair<List<Int>, Long> =
        generatePermutations((0..4).toList())
            .map { it to amplify(it) }
            .maxBy { it.second }!!
}

class LoopBackAmplifierSequence(program: Program) {
    private val programs: List<Program> = (0..4).map { program.copy() }

    // TODO this is hideous
    private fun amplify(phases: List<Int>): Long {
        val outputs =
            programs.zip(phases).map { (program, phase) -> program.rerun(listOf(phase.toLong())).second }
                .toMutableList()
        // Input first amplification
        outputs[0] += programs[0].run(listOf(0)).second
        while (programs.any { it.status() != Program.ProgramStatus.Halt }) {
            programs.withIndex().map { (index, program) ->
                val inputs = outputs[(programs.size + index - 1) % programs.size]
                if (inputs.isNotEmpty()) {
                    outputs[index] = program.run(inputs).second
                    outputs[(programs.size + index - 1) % programs.size] = emptyList()
                }
            }
        }
        return outputs[programs.size - 1].first()
    }

    fun bestPhaseSetting(): Pair<List<Int>, Long> =
        generatePermutations((5..9).toList())
            .map { it to amplify(it) }
            .maxBy { it.second }!!
}

fun main() {
    val inp = object {}.javaClass.getResource("/day7/input.txt").readText().trim()
    val instructions = inp.split(',').map { it.toLongOrNull() }.sequence() ?: return
    val program = Program(instructions.toList())
    println(AmplifierSequence(program).bestPhaseSetting().second)
    println(LoopBackAmplifierSequence(program).bestPhaseSetting().second)
}
