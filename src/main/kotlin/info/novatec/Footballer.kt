package info.novatec

import io.micronaut.core.annotation.Introspected
import io.micronaut.data.annotation.MappedEntity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Introspected
@MappedEntity
data class Footballer(
    @Id @GeneratedValue var id: Long?,
    val firstName: String,
    val lastName: String,
    val position: String
)
