package org.example.game.infrastructure.secondary

import org.example.common.interfaces.InMemoryRepositoryInterface
import org.example.game.domain.Game
import org.example.game.domain.Player
import java.util.UUID

class Repository : InMemoryRepositoryInterface<Game> {
    override val store: MutableMap<UUID, Game> = mutableMapOf()

    override fun save(entity: Game) {
        store[entity.getUUID()] = entity
    }

    override fun remove(entity: Game) {
        store.remove(entity.getUUID())
    }

    override fun findById(uuid: UUID): Game {
        if (!store.contains(uuid)) {
            throw IllegalAccessException("Repository does not contain a game with this UUID")
        }

        return store[uuid]!!
    }

    fun findGamesByPlayer(player: Player): List<Game> {
        return store
            .filter { it.value.firstPlayer == player || it.value.secondPlayer == player }
            .map { it.value }
    }
}
