package day11

import util.Program
import util.sequence

enum class Direction {
    Up {
        override fun left(): Direction = Left
        override fun right(): Direction = Right
    },
    Down {
        override fun left(): Direction = Right
        override fun right(): Direction = Left
    },
    Left {
        override fun left(): Direction = Down
        override fun right(): Direction = Up
    },
    Right {
        override fun left(): Direction = Up
        override fun right(): Direction = Down
    };

    abstract fun left(): Direction
    abstract fun right(): Direction
}

enum class Color(val value: Long) {
    Black(0),
    White(1)
}

data class Point(val x: Int, val y: Int) {
    operator fun plus(dir: Direction): Point = when (dir) {
        Direction.Up -> copy(y = y + 1)
        Direction.Down -> copy(y = y - 1)
        Direction.Right -> copy(x = x + 1)
        Direction.Left -> copy(x = x - 1)
    }
}

// TODO IDEA complains for tailrec function with inited arguments, read the docs what can go wrong
// TODO To test this I need mocks or learn how to make intcode programs, learn how to do mocking in Kotlin
tailrec fun runRobot(
    program: Program,
    position: Point = Point(0, 0),
    direction: Direction = Direction.Up,
    visited: Map<Point, Color> = emptyMap()
): Map<Point, Color>? {
    if (program.status() == Program.ProgramStatus.Halt) return visited
    val color = visited.getOrDefault(position, Color.Black)
    val outs = program.run(listOf(color.value)).second
    if (outs.size != 2) return null

    val newDir = if (outs[1] == 0L) direction.left() else direction.right()
    val newCol = if (outs[0] == 0L) Color.Black else Color.White
    return runRobot(program, position + newDir, newDir, visited + Pair(position, newCol))
}

fun main() {
    val inp = object {}.javaClass.getResource("/day11/input.txt").readText().trim()
    val instructions = inp.split(',').map { it.toLongOrNull() }.sequence() ?: return
    val program = Program(instructions.toList())
    val initSeq = runRobot(program.copy()) ?: return
    println(initSeq.size)
    val painted = runRobot(program.copy(), visited = mapOf(Point(0, 0) to Color.White)) ?: return

    val (minX, maxX) = painted.keys.fold(Pair(Int.MAX_VALUE, Int.MIN_VALUE)) { acc, point ->
        Pair(minOf(acc.first, point.x), maxOf(acc.second, point.x))
    }
    val (minY, maxY) = painted.keys.fold(Pair(Int.MAX_VALUE, Int.MIN_VALUE)) { acc, point ->
        Pair(minOf(acc.first, point.y), maxOf(acc.second, point.y))
    }
    for (y in maxY downTo minY) {
        for (x in minX..maxX) {
            print(
                when (painted.getOrDefault(Point(x, y), Color.Black)) {
                    Color.Black -> ' '
                    Color.White -> '█'
                }
            )
        }
        print('\n')
    }
}
