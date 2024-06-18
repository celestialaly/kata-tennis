package org.example.stadium.orchestration

import org.example.stadium.domain.Stadium
import org.example.stadium.infrastructure.secondary.Repository
import java.util.UUID

class GameNotFoundException: Exception()
class StadiumHasReachedMaximumCapacityException: Exception()

interface GameSpectatorOrchestrator {
    fun addSpectator(numberToAdd: Int, gameUUID: UUID, stadiumUUID: UUID)
    fun getSpectatorCount(gameUUID: UUID, stadiumUUID: UUID): Int
}

class GameSpectatorOrchestratorAdapter(private val repository: Repository): GameSpectatorOrchestrator {
    override fun addSpectator(numberToAdd: Int, gameUUID: UUID, stadiumUUID: UUID) {
        val stadium: Stadium = repository.findById(stadiumUUID)
        val game = stadium.getGame(gameUUID)

        // TODO est-ce que ce if doit être dans l'orchestrator ou dans GameSpectator?
        if (game.spectatorCount() + numberToAdd > stadium.capacity) {
            throw StadiumHasReachedMaximumCapacityException()
        }

        game.addSpectator(numberToAdd)
        repository.save(stadium)
    }

    override fun getSpectatorCount(gameUUID: UUID, stadiumUUID: UUID): Int {
        val stadium: Stadium = repository.findById(stadiumUUID)
        val game = stadium.getGame(gameUUID)

        return game.spectatorCount()
    }
}