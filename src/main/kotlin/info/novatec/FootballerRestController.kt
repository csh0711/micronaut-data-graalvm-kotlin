package info.novatec

import io.micronaut.http.annotation.*
import java.util.*

@Controller("/footballers")
class FootballerRestController(
        private val repository: FootballerRepository
) {

    @Get("/")
    fun search(@QueryValue("position") position: String?): List<Footballer> {
        return if (position != null) {
            repository.findByPosition(position)
        } else {
            repository.findAll().toList()
        }
    }

    @Get("/{id}")
    fun get(id: Long) = repository.findById(id)

    @Post("/")
    fun create(@Body footballer: Footballer) = repository.save(footballer)

    @Delete("/{id}")
    fun delete(id: Long) = repository.deleteById(id)
}