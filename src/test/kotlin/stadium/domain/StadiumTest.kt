package stadium.domain

import io.kotest.matchers.shouldBe
import org.example.common.valueObject.ObjectId
import org.example.stadium.domain.GameSpectator
import org.example.stadium.orchestration.GameNotFoundException
import org.junit.jupiter.api.*
import stadium.common.StadiumFixture

class StadiumTest {
    @Test
    fun `should be able to add a new game`() {
        // given
        val stadium = StadiumFixture.getStadium()

        // when
        val gameId = ObjectId.create()
        stadium.addGame(GameSpectator(gameId))

        // then
        stadium.hasGame(gameId) shouldBe true
    }

    @Test
    fun `should throw error on game not found`() {
        // given
        val stadium = StadiumFixture.getStadium()

        // when
        val gameId = ObjectId.create()

        // then
        stadium.hasGame(gameId) shouldBe false
        assertThrows<GameNotFoundException> { stadium.getGame(gameId) }
    }
}