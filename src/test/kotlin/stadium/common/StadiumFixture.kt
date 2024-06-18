package stadium.common

import org.example.common.acl.StadiumInformation
import org.example.stadium.domain.Stadium

object StadiumFixtureConstant {
    const val NAME = "Rolland Garros"
    const val MAX_CAPACITY = 20
}

class StadiumFixture {
    companion object {
        fun getStadiumInformation(): StadiumInformation {
            return StadiumInformation(
                StadiumFixtureConstant.NAME,
                StadiumFixtureConstant.MAX_CAPACITY,
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