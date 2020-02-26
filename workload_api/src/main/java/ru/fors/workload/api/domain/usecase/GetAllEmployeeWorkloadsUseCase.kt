package ru.fors.workload.api.domain.usecase

import ru.fors.entity.workload.Workload

interface GetAllEmployeeWorkloadsUseCase {
    fun execute(id: Long): List<Workload>
}