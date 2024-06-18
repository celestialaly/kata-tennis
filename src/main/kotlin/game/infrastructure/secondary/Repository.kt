package org.example.game.infrastructure.secondary

import org.example.common.interfaces.InMemoryRepositoryInterface
import org.example.common.valueObject.ObjectId
import org.example.game.domain.Game
import org.example.game.domain.Player
import java.util.UUID

class Repository : InMemoryRepositoryInterface<Game> {
    private val store: MutableMap<ObjectId, Game> = mutableMapOf()

    override fun save(entity: Game) {
        store[entity.id] = entity
    }

    override fun remove(entity: Game) {
        store.remove(entity.id)
    }

    override fun findById(id: ObjectId): Game {
        if (!store.contains(id)) {
            throw IllegalAccessException("Repository does not contain a game with this UUID")
        }

        return store[id]!!
    }

    fun findGamesByPlayer(player: Player): List<Game> {
        return store
            .filter { it.value.firstPlayer == player || it.value.secondPlayer == player }
            .map { it.value }
    }
}
