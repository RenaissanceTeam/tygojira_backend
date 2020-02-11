package ru.fors.util

import org.junit.Test
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import ru.fors.auth.api.domain.entity.NotAllowedException
import kotlin.test.assertFails
import kotlin.test.assertTrue

class A: Throwable()
class B: Throwable()

class ExtensionsKtTest {

    @Test
    fun `when throwable is not allowed then should throw response status not allowed`() {
        val notAllowed = { throw NotAllowedException("") }
        try {
            runCatching(notAllowed).mapNotAllowed()
            assertFails {  }
        } catch (thr: Throwable) {
            assertTrue { thr is ResponseStatusException && thr.status == HttpStatus.METHOD_NOT_ALLOWED }
        }
    }

    @Test
    fun `when add mapper throwable that should not pass should rethrow caused exception`() {
        val cause = { throw IllegalStateException() }

        try {
            runCatching(cause).withExceptionMapper {
                mapper { if (it is IllegalArgumentException) throw A() }
            }
            assertFails {  }
        } catch (thr: Throwable) {
            assertTrue { thr is IllegalStateException }
        }
    }

    @Test
    fun `when with mapper that recognizes error should throw its error`() {
        val cause = { throw IllegalStateException() }

        try {
            runCatching(cause).withExceptionMapper {
                mapper { if (it is IllegalStateException) throw A() }
            }
            assertFails {  }
        } catch (thr: Throwable) {
            assertTrue { thr is A }
        }
    }

    @Test
    fun `when with mapper notAllowed should throw response status`() {
        val cause = { throw NotAllowedException("") }
        try {
            runCatching(cause).withExceptionMapper {
                notAllowed()
            }
            assertFails {  }
        } catch (thr: Throwable) {
            assertTrue { thr is ResponseStatusException && thr.status == HttpStatus.METHOD_NOT_ALLOWED }
        }
    }

    @Test
    fun `when with mapper and used response status mapper should throw response status exception`() {
        val cause = { throw A() }
        try {
            runCatching(cause).withExceptionMapper {
                notAllowed()
                responseStatus({ it is A }, HttpStatus.NOT_FOUND)
            }
            assertFails {  }
        } catch (thr: Throwable) {
            assertTrue { thr is ResponseStatusException && thr.status == HttpStatus.NOT_FOUND }
        }
    }
}