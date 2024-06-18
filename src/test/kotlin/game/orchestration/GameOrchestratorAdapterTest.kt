package game.orchestration

import io.kotest.matchers.collections.shouldBeOneOf
import io.kotest.matchers.shouldBe
import org.example.common.valueObject.ObjectId
import org.example.game.domain.GameData
import org.example.game.domain.GameStillInProgressException
import org.example.game.domain.Player
import org.example.game.infrastructure.secondary.Repository
import org.example.game.orchestration.GameOrchestrator
import org.example.game.orchestration.GameOrchestratorAdapter
import org.junit.jupiter.api.*
import java.util.*

class GameOrchestratorAdapterTest {
    private fun getIdFromNewGame(repository: Repository): ObjectId {
        val gameData = GameData(
            Player("Julie"),
            Player("Lily"),
        )

        val adapter = GameOrchestratorAdapter.create(repository, gameData)
        adapter.playMatch()

        return adapter.getGameId()
    }

    @Test
    fun `should play an entire game and get the winner`() {
        val repository = Repository()
        val gameData = GameData(
            Player("Julie"),
            Player("Lily"),
        )
        val orchestrator: GameOrchestrator = GameOrchestratorAdapter.create(repository, gameData)

        assertThrows<GameStillInProgressException> {
            orchestrator.getWinner()
        }

        orchestrator.playMatch()
        val winner = orchestrator.getWinner()

        winner shouldBeOneOf listOf(gameData.firstPlayer, gameData.secondPlayer)
    }

    @Test
    fun `should load an existing game`() {
        val repository = Repository()
        val gameId = getIdFromNewGame(repository)

        GameOrchestratorAdapter.load(repository, gameId).getGameId() shouldBe gameId
    }
}