package ru.fors.auth.data.controller

import org.springframework.web.bind.annotation.*
import ru.fors.auth.api.domain.dto.PasswordDto
import ru.fors.auth.api.domain.usecase.ChangePasswordUseCase
import ru.fors.auth.data.utils.mapThrowable

@RestController
@RequestMapping("/users")
class UserController(
        private val changePasswordUseCase: ChangePasswordUseCase
) {

    @PostMapping("/{username}")
    fun changePassword(@PathVariable username: String, @RequestBody passwordDto: PasswordDto) {
        changePasswordUseCase.runCatching { execute(username, passwordDto.password) }
                .onFailure(::mapThrowable)
                .getOrThrow()
    }
}