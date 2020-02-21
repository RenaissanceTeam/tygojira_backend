package ru.fors.util.mapper

import org.springframework.http.HttpStatus
import ru.fors.util.extensions.withExceptionMapper
import java.text.SimpleDateFormat
import java.util.*

class StringDateMapper {
    fun map(string: String): Date {
        return dateFormatter.runCatching { parse(string) }
                .withExceptionMapper {
                    responseStatus({ true }, HttpStatus.BAD_REQUEST, "Date format should be: $dateFormat")
                }
                .getOrThrow()
    }

    fun map(date: Date): String {
        return dateFormatter.format(date)
    }

    companion object {
        private const val dateFormat = "dd-MM-yyyy"
        private val dateFormatter = SimpleDateFormat(dateFormat)
    }
}