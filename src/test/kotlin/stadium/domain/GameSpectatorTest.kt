package stadium.domain

import io.kotest.matchers.shouldBe
import org.example.stadium.domain.GameSpectator
import org.junit.jupiter.api.*
import java.util.*

class GameSpectatorTest {
    @Test
    fun `should be able to add spectators to the game`() {
        // given
        val game = GameSpectator(UUID.randomUUID())

        // when
        game.addSpectator(10)

        // then
        game.spectatorCount() shouldBe 10
    }
}