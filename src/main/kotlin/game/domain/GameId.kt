package org.example.game.domain

import java.util.*

class GameId(val value: UUID) {
    companion object {
        fun create(): GameId {
            return GameId(UUID.randomUUID())
        }
    }
}