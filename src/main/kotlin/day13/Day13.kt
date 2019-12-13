package day13

import util.IntVec2
import util.Program
import util.chunk
import util.sequence
import kotlin.math.sign

private const val BLOCK_ID = 2L
private const val PADDLE_ID = 3L
private const val BALL_ID = 4L

fun nBlocks(output: Map<IntVec2, Long>): Int =
    output.count { it.value == BLOCK_ID }

fun parseScreen(output: List<Long>): Map<IntVec2, Long>? =
    output
        .chunk(3)
        ?.map { IntVec2(it[0].toInt(), it[1].toInt()) to it[2] }
        ?.toMap()

fun getScore(screen: Map<IntVec2, Long>): Long? = screen[IntVec2(-1, 0)]
fun getBallPosition(screen: Map<IntVec2, Long>): IntVec2? = screen.entries.find { it.value == BALL_ID }?.key
fun getPaddlePosition(screen: Map<IntVec2, Long>): IntVec2? = screen.entries.find { it.value == PADDLE_ID }?.key

tailrec fun autoPlay(program: Program, prevScreen: Map<IntVec2, Long>): Long? {
    return when {
        nBlocks(prevScreen) == 0 -> getScore(prevScreen)
        else -> {
            val ballPos = getBallPosition(prevScreen) ?: return null
            val paddlePos = getPaddlePosition(prevScreen) ?: return null
            val out = parseScreen(program.run(listOf((ballPos.x - paddlePos.x).sign.toLong())).second) ?: return null
            autoPlay(program, prevScreen + out)
        }
    }
}

fun main() {
    val inp = object {}.javaClass.getResource("/day13/input.txt").readText().trim()
    val instructions = inp.split(',').map { it.toLongOrNull() }.sequence() ?: return
    val program = Program(instructions.toList())
    val out = parseScreen(program.run().second) ?: return
    println(nBlocks(out))

    program.reset()
    program.setMemory(0, 2)
    val initialScreen = parseScreen(program.run().second) ?: return
    println(autoPlay(program, initialScreen))
}
