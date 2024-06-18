package game.domain

import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import org.example.game.domain.*
import org.example.stadium.domain.GameSpectator
import org.example.stadium.domain.Stadium
import org.junit.jupiter.api.*

class GameTest {
    private fun initGame(): Game {
        val player1 = Player("Julie")
        val player2 = Player("Lily")

        return Game(player1, player2)
    }

    private fun setDraw(game: Game) {
        for (i in 1..3) {
            game.firstPlayerWinRound()
        }

        for (i in 1..3) {
            game.secondPlayerWinRound()
        }
    }

    @Test
    fun `should have status in progress with players`() {
        // Given
        val player1 = Player("Julie")
        val player2 = Player("Lily")

        // When
        val game = Game(player1, player2)

        // Then
        game should {
            it.firstPlayer shouldBe player1
            it.secondPlayer shouldBe player2
            it.getStatus() shouldBe GameStatus.IN_PROGRESS
        }
    }

    @Test
    fun `should players play 2 rounds and each players get 1 point`() {
        val game: Game = initGame()

        game.getRounds() shouldBeExactly 0

        // first round
        game.firstPlayerWinRound()

        game.getRounds() shouldBeExactly 1
        game.getFirstPlayerScore() shouldBe "15"
        game.getSecondPlayerScore() shouldBe "0"

        // second round
        game.secondPlayerWinRound()

        game.getRounds() shouldBeExactly 2
        game.getFirstPlayerScore() shouldBe "15"
        game.getSecondPlayerScore() shouldBe "15"
    }

    @Test
    fun `should players play a match and player 1 win`() {
        val game: Game = initGame()

        assertThrows<GameStillInProgressException> {
            game.getWinner()
        }

        for (i in 1..4) {
            game.firstPlayerWinRound()
        }

        game.getStatus() shouldBe GameStatus.FINISHED
        game.getWinner() shouldBe game.firstPlayer

        assertThrows<GameFinishedException> {
            game.firstPlayerWinRound()
        }
    }

    @Test
    fun `should players draws`() {
        val game = initGame()
        setDraw(game)

        // 40-40 is considered draw
        assertThrows<GameStillInProgressException> {
            game.getWinner()
        }
    }

    @Test
    fun `should players trade rounds & stay in draws`() {
        val game = initGame()
        setDraw(game)

        // players trade rounds and stays in draw
        game.firstPlayerWinRound()
        game.secondPlayerWinRound()
        game.firstPlayerWinRound()
        game.secondPlayerWinRound()
        assertThrows<GameStillInProgressException> {
            game.getWinner()
        }
    }

    @Test
    fun `should player 1 win after draw`() {
        val game = initGame()
        setDraw(game)

        // finally player 1 wins
        game.firstPlayerWinRound()
        game.firstPlayerWinRound()
        game.getWinner() shouldBe game.firstPlayer
    }
}