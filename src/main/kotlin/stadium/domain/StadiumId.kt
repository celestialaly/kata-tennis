package org.example.stadium.domain

import org.example.common.interfaces.UUIDInterface
import java.util.UUID

class StadiumId(val value: UUID) {
    companion object {
        fun create(): StadiumId {
            return StadiumId(UUID.randomUUID())
        }
    }
}