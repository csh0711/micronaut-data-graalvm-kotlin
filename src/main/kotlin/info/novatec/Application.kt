package info.novatec

import io.micronaut.core.annotation.TypeHint
import io.micronaut.runtime.Micronaut.*

@TypeHint(org.postgresql.Driver::class)
class Application

fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("info.novatec")
		.start()
}
