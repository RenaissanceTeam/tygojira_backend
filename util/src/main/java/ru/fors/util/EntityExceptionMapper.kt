package ru.fors.util

object EntityExceptionMapper {
    var mapper: (ExceptionMapper.() -> Unit) = {}
}