package ru.fors.workload.request.data.repo

import org.springframework.data.jpa.repository.JpaRepository
import ru.fors.entity.workload.ActivityWorkload
import ru.fors.entity.workload.Workload

interface ActivityWorkloadRepo : JpaRepository<ActivityWorkload, Long> {
    fun findByWorkloadsContaining(workload: Workload): ActivityWorkload
}