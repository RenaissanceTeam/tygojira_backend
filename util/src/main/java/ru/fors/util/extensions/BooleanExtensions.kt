package ru.fors.util.extensions

fun Boolean.then(block: () -> Unit) {
    if (this) block()
}