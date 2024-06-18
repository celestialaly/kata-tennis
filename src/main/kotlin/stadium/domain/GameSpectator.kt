package org.example.stadium.domain

import java.util.*

class GameSpectator(val gameUUID: UUID) {
    private var spectator: Int = 0

    fun addSpectator(number: Int = 1) {
        if (number < 1) throw IllegalArgumentException("Number of spectators to add must be at least 1")

        spectator += number
    }

    fun spectatorCount(): Int {
        return spectator
    }
}