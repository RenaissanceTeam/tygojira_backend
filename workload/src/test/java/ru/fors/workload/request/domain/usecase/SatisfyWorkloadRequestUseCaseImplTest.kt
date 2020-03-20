package ru.fors.workload.request.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import org.junit.Before
import org.junit.Test
import ru.fors.entity.employee.Employee
import ru.fors.entity.workload.WorkUnit
import ru.fors.entity.workload.Workload
import java.time.LocalDate
import kotlin.test.assertTrue

class SatisfyWorkloadRequestUseCaseImplTest {

    private lateinit var uc: SatisfyWorkloadRequestUseCaseImpl
    private val employee = Employee(1L, "", "", "", "", "")
    @Before
    fun setUp() {
        uc = SatisfyWorkloadRequestUseCaseImpl(mock(), mock(), mock(), mock(), mock(), mock(), mock(), mock())
    }


    @Test
    fun `no previous - all new`() {

        val result = uc.mergeWorkload(Workload(
                workunits = listOf(

                ),
                employee = employee,
                id = 1L
        ), listOf(
                WorkUnit(date = LocalDate.parse("2020-01-02"), hours = 2),
                WorkUnit(date = LocalDate.parse("2020-01-03"), hours = 2)
        ))

        assertTrue { result.workunits.size == 2 }
    }

    @Test
    fun `previous before date - should save both`() {

        val result = uc.mergeWorkload(Workload(
                workunits = listOf(
                        WorkUnit(date = LocalDate.parse("2020-01-01"), hours = 2)

                ),
                employee = employee,
                id = 1L
        ), listOf(
                WorkUnit(date = LocalDate.parse("2020-01-02"), hours = 2),
                WorkUnit(date = LocalDate.parse("2020-01-03"), hours = 2)
        ))

        assertTrue { result.workunits.size == 3 }
    }

    @Test
    fun `previous after date - should save both`() {

        val result = uc.mergeWorkload(Workload(
                workunits = listOf(
                        WorkUnit(date = LocalDate.parse("2020-01-04"), hours = 2)

                ),
                employee = employee,
                id = 1L
        ), listOf(
                WorkUnit(date = LocalDate.parse("2020-01-02"), hours = 2),
                WorkUnit(date = LocalDate.parse("2020-01-03"), hours = 2)
        ))

        assertTrue { result.workunits.size == 3 }
    }

    @Test
    fun `previous in between date - should replace only overlap`() {

        val result = uc.mergeWorkload(Workload(
                workunits = listOf(
                        WorkUnit(date = LocalDate.parse("2020-01-04"), hours = 2)
                ),
                employee = employee,
                id = 1L
        ), listOf(
                WorkUnit(date = LocalDate.parse("2020-01-01"), hours = 2),
                WorkUnit(date = LocalDate.parse("2020-01-10"), hours = 2)
        ))

        assertTrue { result.workunits.size == 2 }
    }
}