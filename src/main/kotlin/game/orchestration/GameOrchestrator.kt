package org.example.game.orchestration

import org.example.game.domain.Game
import org.example.game.domain.GameData
import org.example.game.domain.GameStatus
import org.example.game.domain.Player
import org.example.game.infrastructure.secondary.Repository
import java.util.UUID

// Port
interface GameOrchestrator {
    fun getGameUUID(): UUID
    fun playMatch()
    fun getWinner(): Player
    fun createNewGame(gameData: GameData)
}

// Adapter
// TODO Est-ce que je dois isoler GameSpectator (dans son propre adaptateur ?) & Stadium différement => et si oui comment ?
class GameOrchestratorAdapter private constructor(
    // TODO : pourquoi c'est initialisé dans le constructeur  et pas dans les propriétés de classe ?
    private val repository: Repository
) : GameOrchestrator {
    private lateinit var game: Game;

    private constructor(repository: Repository, uuid: UUID) : this(repository) {
        game = repository.findById(uuid)
    }

    private constructor(repository: Repository, gameData: GameData) : this(repository) {
        game = Game(gameData.firstPlayer, gameData.secondPlayer)
    }

    companion object {
        fun create(repository: Repository, gameData: GameData): GameOrchestrator = GameOrchestratorAdapter(repository, gameData)
        fun load(repository: Repository, uuid: UUID): GameOrchestrator = GameOrchestratorAdapter(repository, uuid)
    }

    override fun getGameUUID(): UUID {
        return game.getUUID()
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
        game = Game(gameData.firstPlayer, gameData.secondPlayer)
    }
}
