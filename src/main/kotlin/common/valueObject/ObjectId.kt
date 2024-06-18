package org.example.common.valueObject

import java.util.*

class ObjectId(val value: UUID) {
    companion object {
        fun create(): ObjectId {
            return ObjectId(UUID.randomUUID())
        }
    }
}