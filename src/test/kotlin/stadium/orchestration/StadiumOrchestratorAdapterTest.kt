package stadium.orchestration

import io.kotest.matchers.shouldBe
import org.example.common.valueObject.ObjectId
import org.example.stadium.infrastructure.secondary.Repository
import org.example.stadium.infrastructure.secondary.webservice.StadiumWebservice
import org.example.stadium.orchestration.GameAlreadyExistException
import org.example.stadium.orchestration.StadiumOrchestratorAdapter
import org.junit.jupiter.api.*
import stadium.common.StadiumFixture

class StadiumOrchestratorAdapterTest {
    private fun initStadium(repository: Repository): ObjectId {
        val stadium = StadiumFixture.getStadium()

        repository.save(stadium)

        return stadium.id
    }

    @Test
    fun `should throw an error if game already exists`() {
        // given
        val repository = Repository()
        val stadiumWebservice = StadiumWebservice()
        val stadiumId = initStadium(repository)
        val orchestrator = StadiumOrchestratorAdapter(repository, stadiumWebservice)
        val gameId = ObjectId.create()

        // when
        orchestrator.addGame(stadiumId, gameId)

        // then
        assertThrows<GameAlreadyExistException> { orchestrator.addGame(stadiumId, gameId) }
    }

    @Test
    fun `should be able to add game`() {
        // given
        val repository = Repository()
        val stadiumWebservice = StadiumWebservice()
        val stadiumId = initStadium(repository)
        val orchestrator = StadiumOrchestratorAdapter(repository, stadiumWebservice)
        val gameId = ObjectId.create()

        // when
        orchestrator.addGame(stadiumId, gameId)

        // then
        val stadium = repository.findById(stadiumId)
        stadium.hasGame(gameId) shouldBe true
    }
}