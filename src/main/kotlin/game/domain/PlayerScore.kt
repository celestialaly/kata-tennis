package org.example.game.domain

class PlayerScore(private var value: Int = 0): Comparable<PlayerScore> {
    override fun toString(): String {
        return when (value) {
            0 -> "0"
            1 -> "15"
            2 -> "30"
            3 -> "40"
            else -> "Draw"
        }
    }

    fun getValue(): Int {
        return value
    }

    fun addPoint() {
        value++
    }

    override fun compareTo(other: PlayerScore): Int {
        if (value > other.getValue()) return 1
        if (value < other.getValue()) return -1
        return 0
    }

    fun willWinOver(other: PlayerScore): Boolean {
        if (other.getValue() < 3) {
            return value == 3
        }

        return value > other.getValue()
    }
}