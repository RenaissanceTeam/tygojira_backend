package ru.fors.activity.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.activity.api.domain.usecase.GetActivitiesUseCase
import ru.fors.activity.data.repo.ActivityRepo
import ru.fors.employee.api.domain.usecase.CheckUserHasBusinessRoleUseCase
import ru.fors.employee.api.domain.usecase.GetCallingEmployeeUseCase
import ru.fors.entity.activity.Activity
import ru.fors.entity.employee.Role
import ru.fors.pagination.api.domain.entity.Page
import ru.fors.pagination.api.domain.entity.PageRequest
import ru.fors.util.extensions.toPage
import ru.fors.util.extensions.toSpringPageRequest
import ru.fors.workload.api.domain.usecase.GetActivitiesAndWorkloadsForEmployeeUseCase

@Component
class GetActivitiesUseCaseImpl(
        private val checkUserHasBusinessRoleUseCase: CheckUserHasBusinessRoleUseCase,
        private val getCallingEmployeeUseCase: GetCallingEmployeeUseCase,
        private val getActivitiesAndWorkloadsForEmployeeUseCase: GetActivitiesAndWorkloadsForEmployeeUseCase,
        private val repo: ActivityRepo
) : GetActivitiesUseCase {
    override fun execute(pageRequest: PageRequest): Page<Activity> {
        val caller = getCallingEmployeeUseCase.execute()
        return when {
            checkUserHasBusinessRoleUseCase.execute(Role.PROJECT_OFFICE) -> repo.findAll(pageRequest.toSpringPageRequest())
            checkUserHasBusinessRoleUseCase.execute(Role.LINEAR_LEAD) -> repo.findAllByLeadSubdivision(caller.subdivision, pageRequest.toSpringPageRequest())
            checkUserHasBusinessRoleUseCase.execute(Role.PROJECT_LEAD) -> repo.findAllByLead(caller, pageRequest.toSpringPageRequest())
            checkUserHasBusinessRoleUseCase.execute(Role.EMPLOYEE) -> {
                val callerActivities = getActivitiesAndWorkloadsForEmployeeUseCase.execute(caller.id).map { it.activity.id }
                repo.findAllByIdIn(callerActivities, pageRequest.toSpringPageRequest())
            }
            else -> throw IllegalArgumentException("Unknown business role")
        }.toPage()
    }
}