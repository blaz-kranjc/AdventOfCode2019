package day5

import util.sequence
import util.toDigits

class Program(val instructions: List<Int>)

sealed class Accessor {
    abstract fun get(i: Int, list: List<Int>): Int
    abstract fun set(i: Int, list: MutableList<Int>, value: Int)
}

object AccessByValue : Accessor() {
    override fun get(i: Int, list: List<Int>): Int = list[i]
    override fun set(i: Int, list: MutableList<Int>, value: Int) {
        list[i] = value
    }
}

object AccessByPosition : Accessor() {
    override fun get(i: Int, list: List<Int>): Int = list[list[i]]
    override fun set(i: Int, list: MutableList<Int>, value: Int) {
        list[list[i]] = value
    }
}

object InstructionFactory {
    fun parse(value: Int): Pair<Instruction, List<Accessor>>? {
        val accessors =
            (value / 100).toDigits()
                .reversed()
                .map {
                    when (it) {
                        0 -> AccessByPosition
                        1 -> AccessByValue
                        else -> null
                    }
                }
                .sequence()
                ?.toList()
                ?: return null

        return when (value % 100) {
            1 -> Pair(Instruction.ADD, accessors)
            2 -> Pair(Instruction.MULTIPLY, accessors)
            3 -> Pair(Instruction.STORE, accessors)
            4 -> Pair(Instruction.LOAD, accessors)
            5 -> Pair(Instruction.JUMP_IF, accessors)
            6 -> Pair(Instruction.JUMP_IF_NOT, accessors)
            7 -> Pair(Instruction.LT, accessors)
            8 -> Pair(Instruction.EQ, accessors)
            99 -> Pair(Instruction.HALT, accessors)
            else -> null
        }
    }
}

enum class Instruction {
    ADD,
    MULTIPLY,
    STORE,
    LOAD,
    JUMP_IF,
    JUMP_IF_NOT,
    LT,
    EQ,
    HALT
}

tailrec fun run(
    ins: MutableList<Int>,
    inputs: List<Int>,
    current: Int = 0,
    outputs: List<Int> = emptyList()
): List<Int>? {
    val (instruction, accs) = InstructionFactory.parse(ins[current]) ?: return null
    val getArg = { ind: Int -> accs.getOrElse(ind) { AccessByPosition }.get(current + 1 + ind, ins) }
    val setArg = { ind: Int, value: Int -> accs.getOrElse(ind) { AccessByPosition }.set(current + 1 + ind, ins, value) }
    return when (instruction) {
        Instruction.ADD -> {
            setArg(2, getArg(0) + getArg(1))
            run(ins, inputs, current + 4, outputs)
        }
        Instruction.MULTIPLY -> {
            setArg(2, getArg(0) * getArg(1))
            run(ins, inputs, current + 4, outputs)
        }
        Instruction.STORE -> {
            inputs.firstOrNull() ?: return null
            setArg(0, inputs.first())
            run(ins, inputs.drop(1), current + 2, outputs)
        }
        Instruction.LOAD -> run(ins, inputs, current + 2, outputs + getArg(0))
        Instruction.JUMP_IF -> when (getArg(0)) {
            0 -> run(ins, inputs, current + 3, outputs)
            else -> run(ins, inputs, getArg(1), outputs)
        }
        Instruction.JUMP_IF_NOT -> when (getArg(0)) {
            0 -> run(ins, inputs, getArg(1), outputs)
            else -> run(ins, inputs, current + 3, outputs)
        }
        Instruction.LT -> {
            setArg(2, if (getArg(0) < getArg(1)) 1 else 0)
            run(ins, inputs, current + 4, outputs)
        }
        Instruction.EQ -> {
            setArg(2, if (getArg(0) == getArg(1)) 1 else 0)
            run(ins, inputs, current + 4, outputs)
        }
        Instruction.HALT -> outputs
    }
}

fun Program.run(args: List<Int>): List<Int>? =
    run(instructions.toMutableList(), args)

fun main() {
    val inp = object {}.javaClass.getResource("/day5/input.txt").readText().trim()
    val instructions = inp.split(',').map { it.toIntOrNull() }.sequence() ?: return
    val program = Program(instructions.toList())
    println(program.run(listOf(1))?.first { it != 0 })
    println(program.run(listOf(5))?.first { it != 0 })
}
