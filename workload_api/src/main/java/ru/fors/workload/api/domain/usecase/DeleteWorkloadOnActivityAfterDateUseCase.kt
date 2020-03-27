package ru.fors.workload.api.domain.usecase

import java.time.LocalDate

interface DeleteWorkloadOnActivityAfterDateUseCase {
    fun execute(activityId: Long, date: LocalDate)
}