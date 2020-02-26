package ru.fors.workload.request.data.specifications

import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.domain.Specification.where
import ru.fors.entity.workload.WorkUnit
import ru.fors.entity.workload.Workload
import java.time.LocalDate
//
//typealias WorkUnits = List<WorkUnit>
//fun workloadConflictsSpecification(dates: List<LocalDate>) = Specification<Workload> { root, query, criteriaBuilder ->
//    val workunits = root.get<List<WorkUnit>>("workunits")
//
//
//    // select workloads where workunits intersect with dates
//    criteriaBuilder.exists(
//            query.subquery(WorkUnit::class.java)
//                    .where(dates.map {
//                        workunitConflict(it)
//                    })
//
//    )
//}
//
//fun workunitConflict(date: LocalDate) = Specification<WorkUnit> { root, query, criteriaBuilder ->
//    criteriaBuilder.equal(root.get<LocalDate>("date"), date)
//}