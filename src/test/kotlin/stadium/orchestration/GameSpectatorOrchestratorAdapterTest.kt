package stadium.orchestration

import io.kotest.matchers.shouldBe
import org.example.stadium.domain.GameSpectator
import org.example.stadium.domain.Stadium
import org.example.stadium.infrastructure.secondary.Repository
import org.example.stadium.orchestration.GameSpectatorOrchestratorAdapter
import org.example.stadium.orchestration.StadiumHasReachedMaximumCapacityException
import org.junit.jupiter.api.*
import stadium.common.StadiumDataset
import stadium.common.StadiumDatasetConstants
import java.util.UUID

class GameSpectatorOrchestratorAdapterTest {
    private fun initStadium(repository: Repository): Stadium {
        val stadium = StadiumDataset.getStadium()

        repository.save(stadium)
        return stadium
    }

    private fun addGameSpectator(stadium: Stadium): UUID {
        val gameUuid = UUID.randomUUID()
        stadium.addGame(GameSpectator(gameUuid))

        return gameUuid
    }

    @Test
    fun `should be able to add spectators to game`() {
        // given
        val repository = Repository()
        val stadium = initStadium(repository)
        val gameUUID = addGameSpectator(stadium)
        val orchestrator = GameSpectatorOrchestratorAdapter(repository)

        // when
        val spectatorToAdd = 10
        orchestrator.addSpectator(spectatorToAdd, gameUUID, stadium.uuid)

        // then
        orchestrator.getSpectatorCount(gameUUID, stadium.uuid) shouldBe spectatorToAdd
    }

    @Test
    fun `should throw an error when adding spectator if stadium capacity is reached`() {
        // given
        val repository = Repository()
        val stadium = initStadium(repository)
        val gameUUID = addGameSpectator(stadium)
        val orchestrator = GameSpectatorOrchestratorAdapter(repository)

        // when
        val spectatorToAdd = StadiumDatasetConstants.MAX_CAPACITY + 1

        // then
        assertThrows<StadiumHasReachedMaximumCapacityException> {
            orchestrator.addSpectator(spectatorToAdd, gameUUID, stadium.uuid)
        }
    }
}