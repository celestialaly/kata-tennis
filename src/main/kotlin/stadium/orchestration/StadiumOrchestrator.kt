package org.example.stadium.orchestration

import org.example.stadium.domain.GameSpectator
import org.example.stadium.domain.Stadium
import org.example.stadium.infrastructure.secondary.Repository
import java.util.UUID

class GameAlreadyExistException: Exception()

interface StadiumOrchestrator {
    fun addGame(stadiumUUID: UUID, gameUUID: UUID)
    fun isIndoor(id: UUID): Boolean?
}

class StadiumOrchestratorAdapter(private val repository: Repository): StadiumOrchestrator {
    override fun addGame(stadiumUUID: UUID, gameUUID: UUID) {
        val stadium: Stadium = repository.findById(stadiumUUID)

        if (stadium.hasGame(gameUUID)) {
            throw GameAlreadyExistException()
        }

        stadium.addGame(GameSpectator(gameUUID))
        repository.save(stadium)
    }

    override fun isIndoor(id: UUID): Boolean? {
        return repository.findById(id)?.indoor
    }
}