package org.example.stadium.orchestration

import org.example.common.valueObject.ObjectId
import org.example.stadium.domain.Stadium
import org.example.stadium.infrastructure.secondary.Repository
import java.util.UUID

class GameNotFoundException: Exception()
class StadiumHasReachedMaximumCapacityException: Exception()

interface GameSpectatorOrchestrator {
    fun addSpectator(numberToAdd: Int, gameId: ObjectId, stadiumId: ObjectId)
    fun getSpectatorCount(gameId: ObjectId, stadiumId: ObjectId): Int
}

class GameSpectatorOrchestratorAdapter(private val repository: Repository): GameSpectatorOrchestrator {
    override fun addSpectator(numberToAdd: Int, gameId: ObjectId, stadiumId: ObjectId) {
        val stadium: Stadium = repository.findById(stadiumId)
        val game = stadium.getGame(gameId)

        // TODO est-ce que ce if doit être dans l'orchestrator ou dans GameSpectator?
        if (game.spectatorCount() + numberToAdd > stadium.capacity) {
            throw StadiumHasReachedMaximumCapacityException()
        }

        game.addSpectator(numberToAdd)
        repository.save(stadium)
    }

    override fun getSpectatorCount(gameId: ObjectId, stadiumId: ObjectId): Int {
        val stadium: Stadium = repository.findById(stadiumId)
        val game = stadium.getGame(gameId)

        return game.spectatorCount()
    }
}