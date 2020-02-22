package ru.fors.util

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
