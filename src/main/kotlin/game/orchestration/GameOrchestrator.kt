package org.example.game.orchestration

import org.example.game.domain.*
import org.example.game.infrastructure.secondary.GameWriteRepository

// Port
interface GameOrchestrator {
    fun getGameId(): GameId
    fun playRandomMatch()
    fun getWinner(): Player
    fun createNewGame(gameData: GameData)
}

// Adapter
class GameOrchestratorAdapter private constructor(
    private val repository: GameWriteRepository
) : GameOrchestrator {
    private lateinit var game: Game;

    private constructor(repository: GameWriteRepository, id: GameId) : this(repository) {
        game = repository.findById(id) ?: throw NoSuchElementException("The game with id ${id.value} does not exist")
    }

    private constructor(repository: GameWriteRepository, gameData: GameData) : this(repository) {
        createNewGame(gameData)
    }

    companion object {
        fun create(repository: GameWriteRepository, gameData: GameData): GameOrchestrator = GameOrchestratorAdapter(repository, gameData)
        fun load(repository: GameWriteRepository, id: GameId): GameOrchestrator = GameOrchestratorAdapter(repository, id)
    }

    override fun getGameId(): GameId {
        return game.id
    }

    override fun playRandomMatch() {
        while (game.getStatus() != GameStatus.FINISHED) {
            val isFirstPlayerWinner = (1..6).random() < 3;

            if (isFirstPlayerWinner) {
                game.firstPlayerWinRound()
            } else {
                game.secondPlayerWinRound()
            }
        }

        repository.save(game)
    }

    override fun getWinner(): Player {
        return game.getWinner()
    }

    override fun createNewGame(gameData: GameData) {
        game = Game.create(gameData.firstPlayer, gameData.secondPlayer)
    }
}
