package ru.fors.activity.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.activity.api.domain.dto.ActivityDto
import ru.fors.activity.api.domain.usecase.AddActivityUseCase
import ru.fors.activity.data.repo.ActivityRepo
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.employee.api.domain.usecase.GetEmployeeByIdUseCase
import ru.fors.util.extensions.requireOne
import ru.fors.entity.activity.Activity
import ru.fors.entity.employee.Role

@Component
class AddActivityUseCaseImpl(
        private val roleChecker: RoleChecker,
        private val repo: ActivityRepo,
        private val getEmployeeByIdUseCase: GetEmployeeByIdUseCase
) : AddActivityUseCase {
    override fun execute(activity: ActivityDto): Activity {
        roleChecker.requireOne(Role.LINEAR_LEAD)

        return repo.save(
                Activity(
                        name = activity.name,
                        startDate = activity.startDate,
                        endDate = activity.endDate,
                        lead = getEmployeeByIdUseCase.execute(activity.leadId)
                )
        )
    }
}