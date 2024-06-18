package stadium.common

import org.example.common.acl.StadiumInformation
import org.example.stadium.domain.Stadium

object StadiumDatasetConstants {
    const val MAX_CAPACITY = 20
}

class StadiumDataset {
    companion object {
        private fun getStadiumInformation(): StadiumInformation {
            return StadiumInformation(
                "Rolland Garros",
                StadiumDatasetConstants.MAX_CAPACITY,
                true,
                "Paris",
                "France"
            )
        }

        fun getStadium(): Stadium {
            val information = getStadiumInformation()

            return Stadium.create(
                information.name,
                information.spectatorCapacity,
                information.indoor
            )
        }
    }
}