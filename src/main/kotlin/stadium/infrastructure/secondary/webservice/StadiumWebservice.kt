package org.example.stadium.infrastructure.secondary.webservice

import org.example.common.acl.StadiumInformation
import org.example.stadium.domain.Stadium

const val API_INDEX_NAME = 0
const val API_INDEX_CAPACITY = 1
const val API_INDEX_INDOOR = 2
const val API_INDEX_CITY = 3
const val API_INDEX_COUNTRY = 4

// this could be a webservice http call
class StadiumWebservice {
    private val stadiums = mapOf(
        "Rolland Garros" to listOf("Rolland Garros", "15200", "false", "Paris", "France"),
        "Accor Arena" to listOf("Accor Arena", "15500", "true", "Paris", "France"),
    )

    fun retrieveStadiumInformation(name: String): Stadium {
        if (stadiums.containsKey(name)) {
            // convert from API format to StadiumInformation
            return convertDataFromApi(stadiums[name]!!)
        }

        throw NoSuchElementException()
    }

    private fun convertDataFromApi(data: List<String>): StadiumInformation {
        return StadiumInformation(
            data[API_INDEX_NAME],
            data[API_INDEX_CAPACITY].toInt(),
            data[API_INDEX_INDOOR].toBoolean(),
            data[API_INDEX_CITY],
            data[API_INDEX_COUNTRY]
        )
    }

    private fun convertToDomain(StadiumInformation): Stadium {

    }
}
