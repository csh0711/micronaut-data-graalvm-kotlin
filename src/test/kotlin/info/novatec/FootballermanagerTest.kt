package info.novatec

import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.annotation.MicronautTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@MicronautTest
class FootballermanagerTest(
    private val application: EmbeddedApplication<*>
) {

    @Test
    fun testItWorks() {
        assertThat(application.isRunning).isTrue()
    }
}