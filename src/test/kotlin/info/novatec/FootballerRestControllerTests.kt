package info.novatec

import io.micronaut.http.HttpRequest.DELETE
import io.micronaut.http.HttpRequest.POST
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import io.micronaut.test.annotation.MockBean
import io.mockk.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode.NON_EXTENSIBLE
import java.util.*
import javax.inject.Inject

private val repository: FootballerRepository = mockk()

@MicronautTest
class FootballerRestControllerTests {

    @MockBean(FootballerRepository::class)
    fun repository(): FootballerRepository = repository

    @Inject
    @field:Client("/")
    lateinit var client: RxHttpClient

    private val persistedFootballer = Footballer(
        id = 10L,
        lastName = "Kroos",
        firstName = "Toni",
        position = "Midfield"
    )

    @Test
    fun `get all Footballers`() {
        every { repository.findAll() } returns listOf(
            Footballer(id = 1L, lastName = "Gnabry", firstName = "Serge", position = "Midfield"),
            Footballer(id = 2L, lastName = "Waldschmidt", firstName = "Luca", position = "Striker"),
            Footballer(id = 3L, lastName = "Kroos", firstName = "Toni", position = "Midfield")
        )

        val responseJson = """
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
            },
            {
                "id":3,
                "firstName":"Toni",
                "lastName":"Kroos",
                "position":"Midfield"
            }]
        """

        val body = client.toBlocking().retrieve("/footballers")

        JSONAssert.assertEquals(responseJson, body, NON_EXTENSIBLE);
    }

    @Test
    fun `get Footballers by position`() {
        every { repository.findByPosition("Midfield") } returns listOf(
            Footballer(id = 1L, lastName = "Gnabry", firstName = "Serge", position = "Midfield"),
            Footballer(id = 3L, lastName = "Kroos", firstName = "Toni", position = "Midfield")
        )
        val responseJson = """
           [{
                "id":1,
                "firstName":"Serge",
                "lastName":"Gnabry",
                "position":"Midfield"
            },
            {
                "id":3,
                "firstName":"Toni",
                "lastName":"Kroos",
                "position":"Midfield"
            }]
        """

        val body = client.toBlocking().retrieve("/footballers?position=Midfield")

        JSONAssert.assertEquals(responseJson, body, NON_EXTENSIBLE);
    }

    @Test
    fun `get a single Footballer`() {
        every { repository.findById(10L) } returns Optional.ofNullable(persistedFootballer)

        val responseJson = """
            {
                "id":10,
                "firstName":"Toni",
                "lastName":"Kroos",
                "position":"Midfield"
            }
        """

        val body = client.toBlocking().retrieve("/footballers/10")

        JSONAssert.assertEquals(responseJson, body, NON_EXTENSIBLE);
    }

    @Test
    fun `post new Footballer`() {
        every { repository.save(any<Footballer>()) } returns persistedFootballer
        val requestJson = """
            {
                "firstName":"Toni",
                "lastName":"Kroos",
                "position":"Midfield"
            }
        """
        val responseJson = """
            {
                "id":10,
                "firstName":"Toni",
                "lastName":"Kroos",
                "position":"Midfield"
            }
        """

        val body = client.toBlocking().exchange(POST("/footballers", requestJson), String::class.java).body()

        val slot = slot<Footballer>()
        verify { repository().save(capture(slot)) }
        with(slot.captured) {
            assertThat(id).isNull()
            assertThat(lastName).isEqualTo("Kroos")
            assertThat(firstName).isEqualTo("Toni")
            assertThat(position).isEqualTo("Midfield")
        }
        JSONAssert.assertEquals(responseJson, body, NON_EXTENSIBLE);
    }

    @Test
    fun `delete a single Footballer`() {
        every { repository.deleteById(10L) } just Runs

        val body = client.toBlocking().exchange(DELETE("/footballers/10", null), String::class.java).body()

        verify { repository().deleteById(10) }
        assertThat(body).isNull()
    }
}
