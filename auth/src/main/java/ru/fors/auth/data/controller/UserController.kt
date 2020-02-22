package ru.fors.auth.data.controller

import org.springframework.web.bind.annotation.*
import ru.fors.auth.api.domain.dto.PasswordDto
import ru.fors.auth.api.domain.usecase.ChangePasswordUseCase

@RestController
@RequestMapping("/users")
class UserController(
        private val changePasswordUseCase: ChangePasswordUseCase
) {

    @PostMapping("/{username}")
    fun changePassword(@PathVariable username: String, @RequestBody passwordDto: PasswordDto) {
        changePasswordUseCase.execute(username, passwordDto.password)
    }
}