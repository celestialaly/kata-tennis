package org.example.common.interfaces

import java.util.UUID

interface InMemoryRepositoryInterface<T>  {
    fun save(entity: T)
    fun findById(uuid: UUID): T
    fun remove(entity: T)
}