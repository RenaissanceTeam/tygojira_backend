package ru.fors.workload.request.data.repo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.fors.entity.workload.ActivityWorkload
import ru.fors.entity.workload.Workload

interface ActivityWorkloadRepo : JpaRepository<ActivityWorkload, Long> {
    fun findByWorkloadsContaining(workload: Workload): ActivityWorkload?
    fun findByActivityId(id: Long): ActivityWorkload?
    @Query("select aw from ActivityWorkload aw join aw.workloads w join w.employee e where e.id = ?1")
    fun findByEmployeeId(id: Long): List<ActivityWorkload>
}