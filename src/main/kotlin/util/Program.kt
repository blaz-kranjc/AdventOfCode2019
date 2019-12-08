package util

private sealed class Accessor {
    abstract fun get(i: Int, list: List<Int>): Int
    abstract fun set(i: Int, list: MutableList<Int>, value: Int)
}

private object AccessByValue : Accessor() {
    override fun get(i: Int, list: List<Int>): Int = list[i]
    override fun set(i: Int, list: MutableList<Int>, value: Int) {
        list[i] = value
    }
}

private object AccessByPosition : Accessor() {
    override fun get(i: Int, list: List<Int>): Int = list[list[i]]
    override fun set(i: Int, list: MutableList<Int>, value: Int) {
        list[list[i]] = value
    }
}

enum class StopType {
    Suspended,
    Halted,
    Error
}

private object InstructionFactory {
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

private enum class Instruction {
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


class Program(private val programInstruction: List<Int>) {
    enum class ProgramStatus {
        Suspended,
        Halt
    }

    private var status: ProgramStatus = ProgramStatus.Suspended
    private var currentInstructions = programInstruction.toMutableList()
    private var currentPosition = 0

    fun status(): ProgramStatus {
        return status
    }

    fun run(args: List<Int>): Pair<StopType, List<Int>> =
        runImpl(args)

    private fun reset() {
        status = ProgramStatus.Suspended
        currentInstructions = programInstruction.toMutableList()
        currentPosition = 0
    }

    fun copy(): Program {
        val p = Program(programInstruction)
        p.currentPosition = currentPosition
        p.currentInstructions = currentInstructions
        return p
    }

    fun rerun(args: List<Int>): Pair<StopType, List<Int>> {
        reset()
        return runImpl(args)
    }

    private tailrec fun runImpl(inputs: List<Int>, outputs: List<Int> = emptyList()): Pair<StopType, List<Int>> {
        val (instruction, accs) = InstructionFactory.parse(currentInstructions[currentPosition])
            ?: return Pair(StopType.Error, outputs)
        val getArg =
            { ind: Int -> accs.getOrElse(ind) { AccessByPosition }.get(currentPosition + 1 + ind, currentInstructions) }
        val setArg = { ind: Int, value: Int ->
            accs.getOrElse(ind) { AccessByPosition }.set(currentPosition + 1 + ind, currentInstructions, value)
        }
        return when (instruction) {
            Instruction.ADD -> {
                setArg(2, getArg(0) + getArg(1))
                currentPosition += 4
                runImpl(inputs, outputs)
            }
            Instruction.MULTIPLY -> {
                setArg(2, getArg(0) * getArg(1))
                currentPosition += 4
                runImpl(inputs, outputs)
            }
            Instruction.STORE -> {
                inputs.firstOrNull() ?: return Pair(StopType.Suspended, outputs)
                setArg(0, inputs.first())
                currentPosition += 2
                runImpl(inputs.drop(1), outputs)
            }
            Instruction.LOAD -> {
                val newOut = outputs + getArg(0)
                currentPosition += 2
                runImpl(inputs, newOut)
            }
            Instruction.JUMP_IF -> when (getArg(0)) {
                0 -> {
                    currentPosition += 3
                    runImpl(inputs, outputs)
                }
                else -> {
                    currentPosition = getArg(1)
                    runImpl(inputs, outputs)
                }
            }
            Instruction.JUMP_IF_NOT -> when (getArg(0)) {
                0 -> {
                    currentPosition = getArg(1)
                    runImpl(inputs, outputs)
                }
                else -> {
                    currentPosition += 3
                    runImpl(inputs, outputs)
                }
            }
            Instruction.LT -> {
                setArg(2, if (getArg(0) < getArg(1)) 1 else 0)
                currentPosition += 4
                runImpl(inputs, outputs)
            }
            Instruction.EQ -> {
                setArg(2, if (getArg(0) == getArg(1)) 1 else 0)
                currentPosition += 4
                runImpl(inputs, outputs)
            }
            Instruction.HALT -> {
                status = ProgramStatus.Halt
                Pair(StopType.Halted, outputs)
            }
        }
    }
}
