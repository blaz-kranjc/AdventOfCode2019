package day11

import util.*

enum class Color(val value: Int) {
    Black(0),
    White(1);

    companion object {
        fun from(n: Int): Color? = values().find { it.value == n }
    }
}

// TODO IDEA complains for tailrec function with inited arguments, read the docs what can go wrong
// TODO To test this I need mocks or learn how to make intcode programs, learn how to do mocking in Kotlin
tailrec fun runRobot(
    program: Program,
    position: IntVec2 = IntVec2(0, 0),
    direction: Direction = Direction.Up,
    visited: Map<IntVec2, Color> = emptyMap()
): Map<IntVec2, Color>? {
    if (program.status() == Program.ProgramStatus.Halt) return visited
    val color = visited.getOrDefault(position, Color.Black)
    val outs = program.run(listOf(color.value.toLong())).second
    if (outs.size != 2) return null

    val newDir = if (outs[1] == 0L) direction.left() else direction.right()
    val newCol = Color.from(outs[0].toInt()) ?: return null
    return runRobot(program, position + newDir, newDir, visited + Pair(position, newCol))
}

fun main() {
    val inp = object {}.javaClass.getResource("/day11/input.txt").readText().trim()
    val instructions = inp.split(',').map { it.toLongOrNull() }.sequence() ?: return
    val program = Program(instructions.toList())
    val initSeq = runRobot(program) ?: return
    println(initSeq.size)

    program.reset()
    val painted = runRobot(program, visited = mapOf(IntVec2(0, 0) to Color.White)) ?: return
    val (leftDown, rightUp) =
        painted.keys.drop(1).fold(Pair(painted.keys.first(), painted.keys.first())) { (ld, ru), point ->
            Pair(
                IntVec2(minOf(ld.x, point.x), minOf(ld.y, point.y)),
                IntVec2(maxOf(ru.x, point.x), maxOf(ru.y, point.y))
            )
        }
    for (y in rightUp.y downTo leftDown.y) {
        for (x in leftDown.x..rightUp.x) {
            print(
                when (painted.getOrDefault(IntVec2(x, y), Color.Black)) {
                    Color.Black -> ' '
                    Color.White -> '█'
                }
            )
        }
        print('\n')
    }
}
