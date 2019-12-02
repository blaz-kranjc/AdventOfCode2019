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
    return run(instr)?.get(0);
}

fun main() {
    val inp = object {}.javaClass.getResource("/day2/input.txt").readText().trim()
    val program = Program(inp.split(',').map { it.toInt() }.toList())
    println(program.run(12, 2))

    // TODO: this seems like something that could be fun to do with cartesian product of two sequences
    for (n in 0..99)
        for (v in 0..99)
            if (program.run(n, v) == 19690720)
                println (100 * n + v)
}
