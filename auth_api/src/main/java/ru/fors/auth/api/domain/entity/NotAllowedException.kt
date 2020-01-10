package ru.fors.auth.api.domain.entity

class NotAllowedException(msg: String) : Throwable("Not allowed, $msg")

