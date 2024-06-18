package game.secondary

import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.beInstanceOf
import org.example.game.domain.Game
import org.example.game.domain.GameData
import org.example.game.domain.GameId
import org.example.game.domain.Player
import org.example.game.infrastructure.secondary.GameReadRepository
import org.example.game.infrastructure.secondary.GameWriteRepository
import org.example.game.infrastructure.secondary.Repository
import org.example.game.orchestration.GameOrchestratorAdapter
import org.junit.jupiter.api.*

class RepositoryTest {
    private fun initGameWithFirstRoundPlayed(repository: GameWriteRepository): Game {
        val gameData = GameData(
            Player("Julie"),
            Player("Lily"),
        )

        val game = Game.create(gameData.firstPlayer, gameData.secondPlayer)
        game.firstPlayerWinRound()
        repository.save(game)

        return game
    }

    private fun playRandomGame(repository: GameWriteRepository, firstPlayer: Player, secondPlayer: Player): GameId {
        val gameData = GameData(
            firstPlayer,
            secondPlayer,
        )

        val gameOrchestrator = GameOrchestratorAdapter.create(repository, gameData)
        gameOrchestrator.playRandomMatch()

        return gameOrchestrator.getGameId()
    }

    @Test
    fun `should return null on missing game`() {
        val repository: GameReadRepository = Repository()
        val nonExistingGame: Game? = repository.findById(GameId.create())

        nonExistingGame shouldBe null
    }

    @Test
    fun `should game be persisted after playing a round`() {
        val repository: GameWriteRepository = Repository()
        val gameId: GameId = initGameWithFirstRoundPlayed(repository).id
        val game = repository.findById(gameId)!!

        game should beInstanceOf<Game>()
        game.getFirstPlayerScore() shouldBe "15"
    }

    @Test
    fun `should get number of times a player has played matches`() {
        val repository: GameWriteRepository = Repository()

        val mainPlayer = Player("Lily")
        playRandomGame(repository, mainPlayer, Player("Julie"))
        playRandomGame(repository, mainPlayer, Player("Florian"))
        playRandomGame(repository, mainPlayer, Player("Elina"))

        val games = repository.findGamesByPlayer(mainPlayer)
        games.count() shouldBeExactly 3
    }
}