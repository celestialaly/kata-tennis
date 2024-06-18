package org.example.stadium.infrastructure.secondary

import org.example.common.interfaces.InMemoryRepositoryInterface
import org.example.stadium.domain.Stadium
import java.util.*

class Repository: InMemoryRepositoryInterface<Stadium> {
    private val store: MutableMap<UUID, Stadium> = mutableMapOf()

    override fun save(entity: Stadium) {
        store[entity.getUUID()] = entity
    }

    override fun remove(entity: Stadium) {
        store.remove(entity.getUUID())
    }

    override fun findById(uuid: UUID): Stadium {
        if (!store.contains(uuid)) {
            throw IllegalAccessException("Repository does not contain a game with this UUID")
        }

        return store[uuid]!!
    }
}