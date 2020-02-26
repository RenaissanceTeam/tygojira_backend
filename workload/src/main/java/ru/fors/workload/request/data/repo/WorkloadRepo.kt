package ru.fors.workload.request.data.repo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import ru.fors.entity.workload.WorkUnit
import ru.fors.entity.workload.Workload

interface WorkloadRepo : JpaRepository<Workload, Long>, JpaSpecificationExecutor<Workload> {
    fun findByEmployeeId(id: Long): List<Workload>
    fun findByWorkunitsContaining(workunit: WorkUnit): Workload
}