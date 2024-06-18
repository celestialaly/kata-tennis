package org.example.game.domain

import org.example.common.interfaces.UUIDInterface
import java.util.UUID

class GameStillInProgressException(message: String?) : Exception(message) {}
class GameFinishedException(message: String?) : Exception(message) {}

enum class GameStatus {
    IN_PROGRESS,
    FINISHED
}

class Game(val firstPlayer: Player, val secondPlayer: Player): UUIDInterface {
    override var uuid: UUID = generateUUID()
    private var status: GameStatus = GameStatus.IN_PROGRESS
    private var rounds: Int = 0
    private val firstPlayerScore = PlayerScore()
    private val secondPlayerScore = PlayerScore()

    fun getStatus(): GameStatus {
        return status
    }

    fun getRounds(): Int {
        return rounds
    }

    fun getFirstPlayerScore(): String {
        return firstPlayerScore.toString()
    }

    fun getSecondPlayerScore(): String {
        return secondPlayerScore.toString()
    }

    fun getWinner(): Player {
        if (status != GameStatus.FINISHED) {
            throw GameStillInProgressException("Game is not finished")
        }

        if (firstPlayerScore.compareTo(secondPlayerScore) == 1) {
            return firstPlayer
        }

        return secondPlayer
    }

    fun firstPlayerWinRound() {
        addPointToPlayerScore(firstPlayerScore, secondPlayerScore)
    }

    fun secondPlayerWinRound() {
        addPointToPlayerScore(secondPlayerScore, firstPlayerScore)
    }

    private fun addPointToPlayerScore(winnerPlayerScore: PlayerScore, looserPlayerScore: PlayerScore) {
        if (status == GameStatus.FINISHED) {
            throw GameFinishedException("Game is over")
        }

        if (winnerPlayerScore.willWinOver(looserPlayerScore)) {
            finishGame()
        }

        winnerPlayerScore.addPoint()
        rounds++
    }

    private fun finishGame() {
        status = GameStatus.FINISHED
    }
}
