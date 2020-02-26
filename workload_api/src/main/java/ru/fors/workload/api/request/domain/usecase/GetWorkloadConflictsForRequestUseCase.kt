package ru.fors.workload.api.request.domain.usecase

import ru.fors.entity.workload.Conflict

interface GetWorkloadConflictsForRequestUseCase {
    fun execute(id: Long): List<Conflict>
}