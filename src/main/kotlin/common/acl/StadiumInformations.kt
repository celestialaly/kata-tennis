package org.example.common.acl

data class StadiumInformation(
    val name: String,
    val spectatorCapacity: Int,
    val indoor: Boolean,
    val city: String,
    val country: String
)