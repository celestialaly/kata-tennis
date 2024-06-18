package org.example.common.interfaces

import java.util.*

interface UUIDInterface {
    val uuid: UUID

    fun getUUID(): UUID {
        return uuid
    }

    fun generateUUID(): UUID {
        return UUID.randomUUID()
    }
}