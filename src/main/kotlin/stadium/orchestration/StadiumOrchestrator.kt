package org.example.stadium.orchestration

import org.example.common.valueObject.ObjectId
import org.example.stadium.domain.GameSpectator
import org.example.stadium.domain.Stadium
import org.example.stadium.infrastructure.secondary.Repository
import org.example.stadium.infrastructure.secondary.webservice.StadiumWebservice

class GameAlreadyExistException: Exception()

interface StadiumOrchestrator {
    fun create(name: String): Stadium
    fun addGame(stadiumId: ObjectId, gameId: ObjectId)
    fun isIndoor(id: ObjectId): Boolean
}

class StadiumOrchestratorAdapter(
    private val repository: Repository,
    private val stadiumWebservice: StadiumWebservice
): StadiumOrchestrator {
    override fun create(name: String): Stadium {
        val stadium = stadiumWebservice.convertToDomain(stadiumWebservice.retrieveStadiumInformation(name))
        repository.save(stadium)

        return stadium
    }

    override fun addGame(stadiumId: ObjectId, gameId: ObjectId) {
        val stadium: Stadium = repository.findById(stadiumId)

        if (stadium.hasGame(gameId)) {
            throw GameAlreadyExistException()
        }

        stadium.addGame(GameSpectator(gameId))
        repository.save(stadium)
    }

    override fun isIndoor(id: ObjectId): Boolean {
        return repository.findById(id).indoor
    }
}