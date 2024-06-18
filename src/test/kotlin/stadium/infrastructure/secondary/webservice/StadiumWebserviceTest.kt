package stadium.infrastructure.secondary.webservice

import io.kotest.matchers.shouldBe
import org.example.stadium.infrastructure.secondary.webservice.StadiumWebservice
import org.junit.jupiter.api.*
import stadium.common.StadiumFixture
import stadium.common.StadiumFixtureConstant

class StadiumWebserviceTest {
    @Test
    fun `should throw exception when stadium data not found in API`() {
        val webservice = StadiumWebservice()

        assertThrows<NoSuchElementException> { webservice.retrieveStadiumInformation("NoStadium") }
    }

    @Test
    fun `should create stadium from API data with success`() {
        // given
        val webservice = StadiumWebservice()

        // when
        val stadium = webservice.convertToDomain(StadiumFixture.getStadiumInformation())

        // then
        stadium.name shouldBe StadiumFixtureConstant.NAME
        stadium.capacity shouldBe StadiumFixtureConstant.MAX_CAPACITY
    }
}