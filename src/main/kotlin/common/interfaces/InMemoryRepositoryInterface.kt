package org.example.common.interfaces

import org.example.common.valueObject.ObjectId
import java.util.UUID

interface InMemoryRepositoryInterface<T>  {
    fun save(entity: T)
    fun findById(id: ObjectId): T
    fun remove(entity: T)
}