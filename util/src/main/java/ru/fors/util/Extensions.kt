package ru.fors.util

fun <T> Result<T>.mapNotAllowed(): Result<T> {
    return onFailure { throw it.whenNotAllowedMapToResponseStatusException() }
}

fun <T> Result<T>.withExceptionMapper(block: ExceptionMapper.() -> Unit): Result<T> {

    return onFailure {
        val exceptionMapper = ExceptionMapper()

        block(exceptionMapper)

        exceptionMapper.runChecksOrRethrow(it.whenNotAllowedMapToResponseStatusException())
    }
}


