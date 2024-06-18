package org.example.stadium.domain

import org.example.stadium.orchestration.GameNotFoundException
import java.util.UUID

class Stadium(
    val id: StadiumId,
    val name: String,
    val capacity: Int,
    val indoor: Boolean,
    val games: MutableMap<UUID, GameSpectator> = mutableMapOf()
) {
    companion object {
        fun create(name: String, capacity: Int, indoor: Boolean): Stadium = Stadium(
            id = StadiumId.create(),
            name = name,
            capacity = capacity,
            indoor = indoor
        )
    }

    fun addGame(game: GameSpectator) {
        games[game.gameUUID] = game
    }

    // TODO: change UUID to GameId
    fun hasGame(uuid: UUID): Boolean {
        return games.contains(uuid)
    }

    fun getGame(uuid: UUID): GameSpectator {
        if (!hasGame(uuid)) {
            throw GameNotFoundException()
        }

        return games[uuid]!!
    }
}