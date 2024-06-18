package org.example.stadium.domain

import org.example.common.valueObject.ObjectId
import org.example.stadium.orchestration.GameNotFoundException

class Stadium(
    val id: ObjectId,
    val name: String,
    val capacity: Int,
    val indoor: Boolean,
    val games: MutableMap<ObjectId, GameSpectator> = mutableMapOf()
) {
    companion object {
        fun create(name: String, capacity: Int, indoor: Boolean): Stadium = Stadium(
            id = ObjectId.create(),
            name = name,
            capacity = capacity,
            indoor = indoor
        )
    }

    fun addGame(game: GameSpectator) {
        games[game.gameId] = game
    }

    fun hasGame(id: ObjectId): Boolean {
        return games.contains(id)
    }

    fun getGame(id: ObjectId): GameSpectator {
        if (!hasGame(id)) {
            throw GameNotFoundException()
        }

        return games[id]!!
    }
}