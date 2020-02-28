package info.novatec

import io.micronaut.core.annotation.Introspected
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Introspected
@Entity
data class Footballer(
        @Id @GeneratedValue var id: Long?,
        val firstName: String?,
        val lastName: String?,
        val position: String?
)