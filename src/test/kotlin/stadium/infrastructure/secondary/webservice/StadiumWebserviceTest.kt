package stadium.infrastructure.secondary.webservice

import io.kotest.matchers.shouldBe
import org.example.stadium.infrastructure.secondary.webservice.StadiumWebservice
import org.junit.jupiter.api.*

private const val VALID_STADIUM_NAME = "Rolland Garros"

class StadiumWebserviceTest {
    @Test
    fun `should throw exception when stadium not found in API`() {
        val webservice = StadiumWebservice()

        assertThrows<NoSuchElementException> { webservice.retrieveStadiumInformation("NoStadium") }
    }

    @Test
    fun `should retrieve stadium information from API with success`() {
        // given
        val webservice = StadiumWebservice()

        // when
        val stadiumInformation = webservice.retrieveStadiumInformation(VALID_STADIUM_NAME)

        // then
        stadiumInformation.name shouldBe VALID_STADIUM_NAME
        stadiumInformation.city shouldBe "Paris"
    }
}