package org.example.game.domain

import org.example.common.valueObject.ObjectId
import org.example.stadium.domain.Stadium

class GameStillInProgressException(message: String?) : Exception(message) {}
class GameFinishedException(message: String?) : Exception(message) {}

enum class GameStatus {
    IN_PROGRESS,
    FINISHED
}

class Game(
    val id: ObjectId,
    val firstPlayer: Player,
    val secondPlayer: Player,
    private var status: GameStatus = GameStatus.IN_PROGRESS,
    private var rounds: Int = 0,
    val firstPlayerScore: PlayerScore = PlayerScore(),
    val secondPlayerScore: PlayerScore = PlayerScore()
) {
    companion object {
        fun create(firstPlayer: Player, secondPlayer: Player): Game = Game(
            id = ObjectId.create(),
            firstPlayer = firstPlayer,
            secondPlayer = secondPlayer
        )
    }

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
