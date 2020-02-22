package ru.fors.util.mapper

import java.text.SimpleDateFormat
import java.util.*

class StringDateMapper {
    fun map(string: String): Date {
        return dateFormatter.runCatching { parse(string) }
                .onFailure { throw  BadDateFormat() }
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