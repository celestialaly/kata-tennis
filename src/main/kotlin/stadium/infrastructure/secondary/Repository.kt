package org.example.stadium.infrastructure.secondary

import org.example.common.interfaces.InMemoryRepositoryInterface
import org.example.common.valueObject.ObjectId
import org.example.stadium.domain.Stadium

class Repository: InMemoryRepositoryInterface<Stadium> {
    private val store: MutableMap<ObjectId, Stadium> = mutableMapOf()

    override fun save(entity: Stadium) {
        store[entity.id] = entity
    }

    override fun remove(entity: Stadium) {
        store.remove(entity.id)
    }

    override fun findById(id: ObjectId): Stadium {
        if (!store.contains(id)) {
            throw IllegalAccessException("Repository does not contain a game with this UUID")
        }

        return store[id]!!
    }
}