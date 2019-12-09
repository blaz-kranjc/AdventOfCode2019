package util

private sealed class Accessor {
    abstract fun get(prog: Program, i: Long): Long
    abstract fun set(prog: Program, i: Long, value: Long)
}

private object AccessByValue : Accessor() {
    override fun get(prog: Program, i: Long): Long = prog.getMemory(i)
    override fun set(prog: Program, i: Long, value: Long) = prog.setMemory(i, value)
}

private object AccessByPosition : Accessor() {
    override fun get(prog: Program, i: Long): Long = prog.getMemory(prog.getMemory(i))
    override fun set(prog: Program, i: Long, value: Long) = prog.setMemory(prog.getMemory(i), value)
}

private object RelativeAccessByPosition : Accessor() {
    override fun get(prog: Program, i: Long): Long = prog.getMemory(prog.getMemory(i) + prog.offset())
    override fun set(prog: Program, i: Long, value: Long) = prog.setMemory(prog.getMemory(i) + prog.offset(), value)
}

enum class StopType {
    Suspended,
    Halted,
    Error
}

private object InstructionFactory {
    fun parse(value: Long): Pair<Instruction, List<Accessor>>? {
        val accessors =
            (value / 100).toDigits()
                .reversed()
                .map {
                    when (it) {
                        0 -> AccessByPosition
                        1 -> AccessByValue
                        2 -> RelativeAccessByPosition
                        else -> null
                    }
                }
                .sequence()
                ?.toList()
                ?: return null

        return when ((value % 100).toInt()) {
            1 -> Pair(Instruction.ADD, accessors)
            2 -> Pair(Instruction.MULTIPLY, accessors)
            3 -> Pair(Instruction.STORE, accessors)
            4 -> Pair(Instruction.LOAD, accessors)
            5 -> Pair(Instruction.JUMP_IF, accessors)
            6 -> Pair(Instruction.JUMP_IF_NOT, accessors)
            7 -> Pair(Instruction.LT, accessors)
            8 -> Pair(Instruction.EQ, accessors)
            9 -> Pair(Instruction.SET_RELATIVE_OFFSET, accessors)
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
    SET_RELATIVE_OFFSET,
    HALT
}


class Program(private val initialInstructions: List<Long>) {
    enum class ProgramStatus {
        Suspended,
        Halt
    }

    private var position: Long = 0
    private var accessOffset: Long = 0
    private var instructions = initialInstructions.toMutableList()
    private val memory: MutableMap<Long, Long> = mutableMapOf()
    private var status: ProgramStatus = ProgramStatus.Suspended

    fun status(): ProgramStatus {
        return status
    }

    fun run(args: List<Long> = emptyList()): Pair<StopType, List<Long>> =
        runImpl(args)

    private fun reset() {
        position = 0
        accessOffset = 0
        instructions = initialInstructions.toMutableList()
        memory.clear()
        status = ProgramStatus.Suspended
    }

    fun copy(): Program {
        val p = Program(initialInstructions)
        p.position = position
        p.accessOffset = accessOffset
        p.instructions = instructions
        p.memory.putAll(memory)
        p.status = status
        return p
    }

    fun rerun(args: List<Long> = emptyList()): Pair<StopType, List<Long>> {
        reset()
        return runImpl(args)
    }

    fun offset(): Long = accessOffset

    fun getMemory(i: Long): Long = when {
        i < instructions.size -> instructions[i.toInt()]
        else -> memory.getOrDefault(i, 0)
    }

    fun setMemory(i: Long, value: Long) = when {
        i < instructions.size -> instructions[i.toInt()] =  value
        else -> memory[i] = value
    }

    private tailrec fun runImpl(inputs: List<Long>, outputs: List<Long> = emptyList()): Pair<StopType, List<Long>> {
        val (instruction, accs) = InstructionFactory.parse(getMemory(position))
            ?: return Pair(StopType.Error, outputs)
        val getArg =
            { ind: Int ->
                accs.getOrElse(ind) { AccessByPosition }
                    .get(this, position + 1 + ind)
            }
        val setArg = { ind: Int, value: Long ->
            accs.getOrElse(ind) { AccessByPosition }
                .set(this, position + 1 + ind, value)
        }
        return when (instruction) {
            Instruction.ADD -> {
                setArg(2, getArg(0) + getArg(1))
                position += 4
                runImpl(inputs, outputs)
            }
            Instruction.MULTIPLY -> {
                setArg(2, getArg(0) * getArg(1))
                position += 4
                runImpl(inputs, outputs)
            }
            Instruction.STORE -> {
                inputs.firstOrNull() ?: return Pair(StopType.Suspended, outputs)
                setArg(0, inputs.first())
                position += 2
                runImpl(inputs.drop(1), outputs)
            }
            Instruction.LOAD -> {
                val newOut = outputs + getArg(0)
                position += 2
                runImpl(inputs, newOut)
            }
            Instruction.JUMP_IF -> when (getArg(0)) {
                0L -> {
                    position += 3
                    runImpl(inputs, outputs)
                }
                else -> {
                    position = getArg(1)
                    runImpl(inputs, outputs)
                }
            }
            Instruction.JUMP_IF_NOT -> when (getArg(0)) {
                0L -> {
                    position = getArg(1)
                    runImpl(inputs, outputs)
                }
                else -> {
                    position += 3
                    runImpl(inputs, outputs)
                }
            }
            Instruction.LT -> {
                setArg(2, if (getArg(0) < getArg(1)) 1 else 0)
                position += 4
                runImpl(inputs, outputs)
            }
            Instruction.EQ -> {
                setArg(2, if (getArg(0) == getArg(1)) 1 else 0)
                position += 4
                runImpl(inputs, outputs)
            }
            Instruction.SET_RELATIVE_OFFSET -> {
                accessOffset += getArg(0)
                position += 2
                runImpl(inputs, outputs)
            }
            Instruction.HALT -> {
                status = ProgramStatus.Halt
                Pair(StopType.Halted, outputs)
            }
        }
    }
}
