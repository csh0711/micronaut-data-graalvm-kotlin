package info.novatec

import io.micronaut.test.annotation.MicronautTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@MicronautTest(environments = ["test"])
class FootballerRepositoryTests(
    private val repository: FootballerRepository
) {

    @Test
    fun `findByPosition returns list of Footballers`() {
        val footballers = listOf(
            Footballer(id = null, lastName = "Gnabry", firstName = "Serge", position = "Midfield"),
            Footballer(id = null, lastName = "Waldschmidt", firstName = "Luca", position = "Striker"),
            Footballer(id = null, lastName = "Kroos", firstName = "Toni", position = "Midfield")
        )
        repository.saveAll(footballers)

        val midfieldFootballers = repository.findByPosition("Midfield")

        assertThat(midfieldFootballers).hasSize(2)
        with(midfieldFootballers[0]) {
            assertThat(id).isNotNull()
            assertThat(lastName).isEqualTo("Gnabry")
            assertThat(firstName).isEqualTo("Serge")
            assertThat(position).isEqualTo("Midfield")
        }
        with(midfieldFootballers[1]) {
            assertThat(id).isNotNull()
            assertThat(lastName).isEqualTo("Kroos")
            assertThat(firstName).isEqualTo("Toni")
            assertThat(position).isEqualTo("Midfield")
        }
    }
}
