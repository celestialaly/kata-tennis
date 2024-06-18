package org.example.game.orchestration

import org.example.common.valueObject.ObjectId
import org.example.game.domain.Game
import org.example.game.domain.GameData
import org.example.game.domain.GameStatus
import org.example.game.domain.Player
import org.example.game.infrastructure.secondary.Repository
import java.util.UUID

// Port
interface GameOrchestrator {
    fun getGameId(): ObjectId
    fun playMatch()
    fun getWinner(): Player
    fun createNewGame(gameData: GameData)
}

// Adapter
class GameOrchestratorAdapter private constructor(
    private val repository: Repository
) : GameOrchestrator {
    private lateinit var game: Game;

    private constructor(repository: Repository, id: ObjectId) : this(repository) {
        game = repository.findById(id)
    }

    private constructor(repository: Repository, gameData: GameData) : this(repository) {
        createNewGame(gameData)
    }

    companion object {
        fun create(repository: Repository, gameData: GameData): GameOrchestrator = GameOrchestratorAdapter(repository, gameData)
        fun load(repository: Repository, id: ObjectId): GameOrchestrator = GameOrchestratorAdapter(repository, id)
    }

    override fun getGameId(): ObjectId {
        return game.id
    }

    override fun playMatch() {
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
