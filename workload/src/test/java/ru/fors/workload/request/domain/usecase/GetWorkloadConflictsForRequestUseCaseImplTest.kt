package ru.fors.workload.request.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import ru.fors.entity.employee.Employee
import ru.fors.entity.workload.ActivityWorkload
import ru.fors.entity.workload.WorkUnit
import ru.fors.entity.workload.Workload
import ru.fors.entity.workload.request.WorkloadRequest
import ru.fors.workload.api.request.domain.usecase.GetWorkloadConflictsForRequestUseCase
import ru.fors.workload.api.request.domain.usecase.GetWorkloadRequestByIdUseCase
import ru.fors.workload.request.data.repo.ActivityWorkloadRepo
import ru.fors.workload.request.data.repo.WorkloadRepo
import java.time.LocalDate
import kotlin.test.assertTrue

class GetWorkloadConflictsForRequestUseCaseImplTest {
    private lateinit var repo: WorkloadRepo
    private lateinit var activityWorkloadRepo: ActivityWorkloadRepo
    private lateinit var getWorkloadRequestByIdUseCase: GetWorkloadRequestByIdUseCase
    private lateinit var useCase: GetWorkloadConflictsForRequestUseCase
    private val requestId = 1L

    private val employeeA = Employee(111L, "", "", "", "", "", listOf())
    private val employeeB = Employee(222L, "", "", "", "", "", listOf())

    private val workUnitId_1 = 2221L
    private val workUnitId_2 = 2222L
    private val workUnitId_3 = 2223L
    private val workUnitId_4 = 2224L

    @Before
    fun setUp() {
        repo = mock {}
        activityWorkloadRepo = mock {}
        getWorkloadRequestByIdUseCase = mock {}
        useCase = GetWorkloadConflictsForRequestUseCaseImpl(repo, activityWorkloadRepo, getWorkloadRequestByIdUseCase)
    }

    @Test
    fun `when have overlapping day but for other employee should return empty`() {
        val date = LocalDate.parse("2007-12-03")
        whenever(getWorkloadRequestByIdUseCase.execute(requestId)).then {
            mock<WorkloadRequest> {

                on { position }.then { "sas" }
                on { skills }.then { listOf<String>() }
                on { employee }.then { employeeA }
                on { workUnits }.then {
                    listOf(
                            WorkUnit(workUnitId_1, date, 8)
                    )
                }
            }
        }

        whenever(repo.findByEmployeeId(employeeB.id)).then {
            listOf(
                    Workload(44, listOf(
                            WorkUnit(workUnitId_2, date, 8)
                    ), employeeB)
            )
        }

        val result = useCase.execute(requestId)

        assertTrue(result.isEmpty())
    }

    @Test
    fun `should return conflict for overlapping full day work and same user`() {
        val date = LocalDate.parse("2007-12-03")
        whenever(getWorkloadRequestByIdUseCase.execute(requestId)).then {
            mock<WorkloadRequest> {

                on { position }.then { "sas" }
                on { skills }.then { listOf<String>() }
                on { employee }.then { employeeA }
                on { workUnits }.then {
                    listOf(
                            WorkUnit(workUnitId_1, date, 8)
                    )
                }
            }
        }


        val savedWork = WorkUnit(workUnitId_2, date, 8)
        val savedWorkload = Workload(44, listOf(savedWork), employeeA)

        whenever(repo.findByWorkunitsContaining(savedWork)).then { savedWorkload }
        whenever(activityWorkloadRepo.findByWorkloadsContaining(savedWorkload)).then { mock<ActivityWorkload>() }

        whenever(repo.findByEmployeeId(employeeA.id)).then {
            listOf(savedWorkload)
        }
        val result = useCase.execute(requestId)

        assertTrue(
                result.first().workUnit == WorkUnit(workUnitId_1, date, 8)
                        && result.first().with.workUnits == listOf(savedWork)
        )
    }

    @Test
    fun `should return all conflicts for overlapping work for same user`() {
        val date = LocalDate.parse("2007-12-03")
        whenever(getWorkloadRequestByIdUseCase.execute(requestId)).then {
            mock<WorkloadRequest> {

                on { position }.then { "sas" }
                on { skills }.then { listOf<String>() }
                on { employee }.then { employeeA }
                on { workUnits }.then {
                    listOf(
                            WorkUnit(workUnitId_1, date, 8)
                    )
                }
            }
        }


        val savedWork_1 = WorkUnit(workUnitId_2, date, 4)
        val savedWork_2 = WorkUnit(workUnitId_3, date, 4)
        val savedWorkload_1 = Workload(44, listOf(savedWork_1), employeeA)
        val savedWorkload_2 = Workload(45, listOf(savedWork_2), employeeA)

        whenever(repo.findByWorkunitsContaining(savedWork_1)).then { savedWorkload_1 }
        whenever(repo.findByWorkunitsContaining(savedWork_2)).then { savedWorkload_2 }

        whenever(activityWorkloadRepo.findByWorkloadsContaining(savedWorkload_1)).then { mock<ActivityWorkload>() }
        whenever(activityWorkloadRepo.findByWorkloadsContaining(savedWorkload_2)).then { mock<ActivityWorkload>() }

        whenever(repo.findByEmployeeId(employeeA.id)).then {
            listOf(savedWorkload_1, savedWorkload_2)
        }
        val result = useCase.execute(requestId)

        assertTrue(
                result.first().workUnit == WorkUnit(workUnitId_1, date, 8)
                        && result.first().with.workUnits == listOf(savedWork_1, savedWork_2)
        )
    }

    @Test
    fun `when less than or equal 8 hours in total should be no conflict`() {
        val date = LocalDate.parse("2007-12-03")
        whenever(getWorkloadRequestByIdUseCase.execute(requestId)).then {
            mock<WorkloadRequest> {

                on { position }.then { "sas" }
                on { skills }.then { listOf<String>() }
                on { employee }.then { employeeA }
                on { workUnits }.then {
                    listOf(
                            WorkUnit(workUnitId_1, date, 2)
                    )
                }
            }
        }


        val savedWork_1 = WorkUnit(workUnitId_2, date, 3)
        val savedWork_2 = WorkUnit(workUnitId_3, date, 3)
        val savedWorkload_1 = Workload(44, listOf(savedWork_1), employeeA)
        val savedWorkload_2 = Workload(45, listOf(savedWork_2), employeeA)

        whenever(repo.findByWorkunitsContaining(savedWork_1)).then { savedWorkload_1 }
        whenever(repo.findByWorkunitsContaining(savedWork_2)).then { savedWorkload_2 }

        whenever(activityWorkloadRepo.findByWorkloadsContaining(savedWorkload_1)).then { mock<ActivityWorkload>() }
        whenever(activityWorkloadRepo.findByWorkloadsContaining(savedWorkload_2)).then { mock<ActivityWorkload>() }

        whenever(repo.findByEmployeeId(employeeA.id)).then {
            listOf(savedWorkload_1, savedWorkload_2)
        }
        val result = useCase.execute(requestId)

        assertTrue(
                result.isEmpty()
        )
    }

    @Test
    fun `when have at least one date with more than 8 hours should return conflict`() {
        val date_1 = LocalDate.parse("2007-12-03")
        val date_2 = LocalDate.parse("2007-12-04")

        whenever(getWorkloadRequestByIdUseCase.execute(requestId)).then {
            mock<WorkloadRequest> {

                on { position }.then { "sas" }
                on { skills }.then { listOf<String>() }
                on { employee }.then { employeeA }
                on { workUnits }.then {
                    listOf(
                            WorkUnit(workUnitId_1, date_1, 2),
                            WorkUnit(workUnitId_4, date_2, 2)
                    )
                }
            }
        }


        val savedWork_1 = WorkUnit(workUnitId_2, date_2, 3)
        val savedWork_2 = WorkUnit(workUnitId_3, date_2, 4)
        val savedWorkload_1 = Workload(44, listOf(savedWork_1), employeeA)
        val savedWorkload_2 = Workload(45, listOf(savedWork_2), employeeA)

        whenever(repo.findByWorkunitsContaining(savedWork_1)).then { savedWorkload_1 }
        whenever(repo.findByWorkunitsContaining(savedWork_2)).then { savedWorkload_2 }

        whenever(activityWorkloadRepo.findByWorkloadsContaining(savedWorkload_1)).then { mock<ActivityWorkload>() }
        whenever(activityWorkloadRepo.findByWorkloadsContaining(savedWorkload_2)).then { mock<ActivityWorkload>() }

        whenever(repo.findByEmployeeId(employeeA.id)).then {
            listOf(savedWorkload_1, savedWorkload_2)
        }
        val result = useCase.execute(requestId)

        assertTrue(
                result.first().workUnit == WorkUnit(workUnitId_4, date_2, 2)
                        && result.first().with.workUnits == listOf(savedWork_1, savedWork_2)
        )
    }
}