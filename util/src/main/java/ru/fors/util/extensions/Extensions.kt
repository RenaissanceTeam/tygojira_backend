package ru.fors.util.extensions

import ru.fors.util.mapper.EntityExceptionMapper
import ru.fors.util.mapper.ExceptionMapper

fun <T> Result<T>.withExceptionMapper(block: ExceptionMapper.() -> Unit): Result<T> {

    return onFailure { throwable ->
        ExceptionMapper().apply {
            block()
            runChecksOrRethrow(throwable)
        }
    }
}

fun <T> Result<T>.withEntityExceptionsMapper(): Result<T> {
    return withExceptionMapper(EntityExceptionMapper.mapper)
}
