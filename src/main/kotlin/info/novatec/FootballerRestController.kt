package info.novatec

import io.micronaut.http.annotation.*

@Controller("/footballers")
class FootballerRestController(
    private val repository: FootballerRepository
) {

    @Get("/")
    fun search(@QueryValue position: String?): List<Footballer> {
        return if (position.isNullOrEmpty()) {
            repository.findAll().toList()
        } else {
            repository.findByPosition(position)
        }
    }

    @Get("/{id}")
    fun get(id: Long) = repository.findById(id)

    @Post("/")
    fun create(@Body footballer: Footballer) = repository.save(footballer)

    @Delete("/{id}")
    fun delete(id: Long) = repository.deleteById(id)
}