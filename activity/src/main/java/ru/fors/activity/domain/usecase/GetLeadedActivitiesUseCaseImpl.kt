package ru.fors.activity.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.activity.api.domain.usecase.GetLeadedActivitiesUseCase
import ru.fors.activity.data.repo.ActivityRepo
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.employee.api.domain.usecase.GetCallingEmployeeUseCase
import ru.fors.entity.activity.Activity
import ru.fors.entity.employee.Role
import ru.fors.util.extensions.requireAny
import ru.fors.util.extensions.requireOne

@Component
class GetLeadedActivitiesUseCaseImpl(
        private val repo: ActivityRepo,
        private val getCallingEmployeeUseCase: GetCallingEmployeeUseCase
) : GetLeadedActivitiesUseCase {
    override fun execute(): List<Activity> {
        return repo.findByLead(getCallingEmployeeUseCase.execute())
    }
}