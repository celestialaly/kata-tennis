package game.secondary

import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.beInstanceOf
import org.example.common.valueObject.ObjectId
import org.example.game.domain.Game
import org.example.game.domain.GameData
import org.example.game.domain.Player
import org.example.game.infrastructure.secondary.Repository
import org.example.game.orchestration.GameOrchestratorAdapter
import org.junit.jupiter.api.*
import java.util.*

class RepositoryTest {
    @Test
    fun `should throw error on missing game`() {
        val repository = Repository()

        assertThrows<IllegalAccessException> {
            repository.findById(ObjectId.create())
        }
    }

    @Test
    fun `should game be persisted`() {
        val repository = Repository()
        val gameData = GameData(
            Player("Julie"),
            Player("Lily"),
        )

        var game = Game.create(gameData.firstPlayer, gameData.secondPlayer)
        game.firstPlayerWinRound()
        repository.save(game)

        game = repository.findById(game.id)

        game should beInstanceOf<Game>()
        game.getFirstPlayerScore() shouldBe "15"
    }

    @Test
    fun `should get number of times a player has played matches`() {
        val repository = Repository()
        val mainPlayer = Player("Lily")
        val gameData = GameData(
            Player("Julie"),
            mainPlayer,
        )

        val gameOrchestrator = GameOrchestratorAdapter.create(repository, gameData)
        gameOrchestrator.playMatch()

        gameOrchestrator.createNewGame(
            GameData(
                Player("Florian"),
                mainPlayer,
            )
        );
        gameOrchestrator.playMatch()

        val games = repository.findGamesByPlayer(mainPlayer)

        games.count() shouldBeExactly 2
    }
}