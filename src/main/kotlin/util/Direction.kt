package util

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

    companion object {
        fun fromChar(dir: Char): Direction? = when (dir) {
            'U' -> Up
            'D' -> Down
            'L' -> Left
            'R' -> Right
            else -> null
        }
    }

    abstract fun left(): Direction
    abstract fun right(): Direction
}

operator fun IntVec2.plus(dir: Direction): IntVec2 = when (dir) {
    Direction.Up -> copy(y = y + 1)
    Direction.Down -> copy(y = y - 1)
    Direction.Right -> copy(x = x + 1)
    Direction.Left -> copy(x = x - 1)
}

