package ru.fors.util.extensions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

fun Throwable.toResponseEntityStatus(status: HttpStatus): ResponseEntity<String> = ResponseEntity(message.orEmpty(), status)