package stadium.orchestration

import io.kotest.matchers.shouldBe
import org.example.stadium.infrastructure.secondary.Repository
import org.example.stadium.orchestration.GameAlreadyExistException
import org.example.stadium.orchestration.StadiumOrchestratorAdapter
import org.junit.jupiter.api.*
import stadium.common.StadiumDataset
import java.util.UUID

class StadiumOrchestratorAdapterTest {
    private fun initStadium(repository: Repository): UUID {
        val stadium = StadiumDataset.getStadium()

        repository.save(stadium)

        return stadium.getUUID()
    }

    @Test
    fun `should throw an error if game already exists`() {
        // given
        val repository = Repository()
        val stadiumUUID = initStadium(repository)
        val orchestrator = StadiumOrchestratorAdapter(repository)
        val gameUUID = UUID.randomUUID()

        // when
        orchestrator.addGame(stadiumUUID, gameUUID)

        // then
        assertThrows<GameAlreadyExistException> { orchestrator.addGame(stadiumUUID, gameUUID) }
    }

    @Test
    fun `should be able to add game`() {
        // given
        val repository = Repository()
        val stadiumUUID = initStadium(repository)
        val orchestrator = StadiumOrchestratorAdapter(repository)
        val gameUUID = UUID.randomUUID()

        // when
        orchestrator.addGame(stadiumUUID, gameUUID)

        // then
        val stadium = repository.findById(stadiumUUID)
        stadium.hasGame(gameUUID) shouldBe true
    }
}