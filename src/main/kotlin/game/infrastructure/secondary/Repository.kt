package org.example.game.infrastructure.secondary

import org.example.game.domain.Game
import org.example.game.domain.GameId
import org.example.game.domain.Player

interface GameReadRepository {
    fun findById(id: GameId): Game?
    fun findGamesByPlayer(player: Player): List<Game>
}

interface GameWriteRepository: GameReadRepository {
    fun save(game: Game)
    fun remove(game: Game)
}

class Repository : GameWriteRepository {
    private val store: MutableMap<GameId, Game> = mutableMapOf()

    override fun save(game: Game) {
        store[game.id] = game
    }

    override fun remove(game: Game) {
        store.remove(game.id)
    }

    override fun findById(id: GameId): Game? {
        return store[id]
    }

    override fun findGamesByPlayer(player: Player): List<Game> {
        return store
            .filter { it.value.firstPlayer == player || it.value.secondPlayer == player }
            .map { it.value }
    }
}
