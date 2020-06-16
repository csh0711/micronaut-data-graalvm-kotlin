package info.novatec

import io.micronaut.core.annotation.TypeHint
import io.micronaut.runtime.Micronaut

@TypeHint(org.postgresql.Driver::class)
object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("info.novatec")
                .mainClass(Application.javaClass)
                .start()
    }
}