package info.novatec

import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import io.micronaut.test.annotation.MockBean
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.compareJSON
import javax.inject.Inject

private val repository: FootballerRepository = mockk()

@MicronautTest
internal class FootballerRestControllerTests {

    @MockBean(FootballerRepository::class)
    fun repository(): FootballerRepository = repository

    @Inject
    @field:Client("/footballers")
    lateinit var client: HttpClient

    @Test
    fun `get all Footballers`() {
        every { repository.findAll() } returns listOf(
            Footballer(id = 1L, lastName = "Gnabry", firstName = "Serge", position = "Midfield"),
            Footballer(id = 2L, lastName = "Waldschmidt", firstName = "Luca", position = "Striker")
        )

        val expectedJson = """
            [{
                "id":1,
                "firstName":"Serge",
                "lastName":"Gnabry",
                "position":"Midfield"
            },
            {
                "id":2,
                "firstName":"Luca",
                "lastName":"Waldschmidt",
                "position":"Striker"
            }]
        """

        val result = client.toBlocking().retrieve("/")

        assertThat(result).isNotNull()
        assertThat(result.compareJSON(expectedJson)).isTrue()
    }
}