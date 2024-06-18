package stadium.domain

import io.kotest.matchers.shouldBe
import org.example.stadium.domain.GameSpectator
import org.example.stadium.orchestration.GameNotFoundException
import org.junit.jupiter.api.*
import stadium.common.StadiumDataset
import java.util.*

class StadiumTest {
    @Test
    fun `should be able to add a new game`() {
        // given
        val stadium = StadiumDataset.getStadium()

        // when
        val gameUUID = UUID.randomUUID()
        stadium.addGame(GameSpectator(gameUUID))

        // then
        stadium.hasGame(gameUUID) shouldBe true
    }

    @Test
    fun `should throw error on game not found`() {
        // given
        val stadium = StadiumDataset.getStadium()

        // when
        val gameUUID = UUID.randomUUID()

        // then
        stadium.hasGame(gameUUID) shouldBe false
        assertThrows<GameNotFoundException> { stadium.getGame(gameUUID) }
    }
}