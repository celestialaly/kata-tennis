package stadium.orchestration

import io.kotest.matchers.shouldBe
import org.example.common.valueObject.ObjectId
import org.example.stadium.domain.GameSpectator
import org.example.stadium.domain.Stadium
import org.example.stadium.infrastructure.secondary.Repository
import org.example.stadium.orchestration.GameSpectatorOrchestratorAdapter
import org.example.stadium.orchestration.StadiumHasReachedMaximumCapacityException
import org.junit.jupiter.api.*
import stadium.common.StadiumFixture
import stadium.common.StadiumFixtureConstant

class GameSpectatorOrchestratorAdapterTest {
    private fun initStadium(repository: Repository): Stadium {
        val stadium = StadiumFixture.getStadium()

        repository.save(stadium)
        return stadium
    }

    private fun addGameSpectator(stadium: Stadium): ObjectId {
        val gameId = ObjectId.create()
        stadium.addGame(GameSpectator(gameId))

        return gameId
    }

    @Test
    fun `should be able to add spectators to game`() {
        // given
        val repository = Repository()
        val stadium = initStadium(repository)
        val gameId = addGameSpectator(stadium)
        val orchestrator = GameSpectatorOrchestratorAdapter(repository)

        // when
        val spectatorToAdd = 10
        orchestrator.addSpectator(spectatorToAdd, gameId, stadium.id)

        // then
        orchestrator.getSpectatorCount(gameId, stadium.id) shouldBe spectatorToAdd
    }

    @Test
    fun `should throw an error when adding spectator if stadium capacity is reached`() {
        // given
        val repository = Repository()
        val stadium = initStadium(repository)
        val gameId = addGameSpectator(stadium)
        val orchestrator = GameSpectatorOrchestratorAdapter(repository)

        // when
        val spectatorToAdd = StadiumFixtureConstant.MAX_CAPACITY + 1

        // then
        assertThrows<StadiumHasReachedMaximumCapacityException> {
            orchestrator.addSpectator(spectatorToAdd, gameId, stadium.id)
        }
    }
}