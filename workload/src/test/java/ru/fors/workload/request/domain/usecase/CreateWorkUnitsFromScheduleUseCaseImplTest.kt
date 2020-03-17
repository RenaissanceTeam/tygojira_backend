package ru.fors.workload.request.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import ru.fors.entity.employee.Employee
import ru.fors.entity.holiday.Holiday
import ru.fors.production.calendar.api.domain.usecase.GetHolidaysUseCase
import ru.fors.workload.api.request.domain.dto.WorkloadScheduleDto
import ru.fors.workload.api.request.domain.usecase.CreateWorkUnitsFromScheduleUseCase
import java.time.LocalDate
import java.time.Year
import kotlin.test.assertFails
import kotlin.test.assertTrue

class CreateWorkUnitsFromScheduleUseCaseImplTest {
    private lateinit var useCase: CreateWorkUnitsFromScheduleUseCase
    private lateinit var getHolidaysUseCase: GetHolidaysUseCase
    private lateinit var employee: Employee
    @Before
    fun setUp() {
        employee = mock {}
        getHolidaysUseCase = mock {}
        useCase = CreateWorkUnitsFromScheduleUseCaseImpl(getHolidaysUseCase)
    }

    @Test
    fun `when start more than end should throw`() {
        val start = LocalDate.parse("2020-01-02")
        val end = LocalDate.parse("2020-01-01")
        val schedule = WorkloadScheduleDto(start, end)
        assertFails { useCase.execute(schedule, employee) }
    }

    @Test
    fun `one day should convert to one work unit`() {
        val start = LocalDate.parse("2020-03-16")
        val end = LocalDate.parse("2020-03-16")
        val schedule = WorkloadScheduleDto(start, end, monday = 8)
        val result = useCase.execute(schedule, employee)
        assertTrue { result.size == 1 && result.first().date == start && result.first().hours == 8 }
    }

    @Test
    fun `when schedule doesn't contain work hours should not add work unit`() {
        val start = LocalDate.parse("2020-03-16")
        val end = LocalDate.parse("2020-03-16")
        val schedule = WorkloadScheduleDto(start, end, tuesday = 8)
        val result = useCase.execute(schedule, employee)
        assertTrue { result.isEmpty() }
    }

    @Test
    fun `one full week should contain 5 work units`() {
        val start = LocalDate.parse("2020-03-16")
        val end = LocalDate.parse("2020-03-23")
        val schedule = WorkloadScheduleDto(start, end, monday = 8, tuesday = 8, wednesday = 8, thursday = 8, friday = 8)
        val result = useCase.execute(schedule, employee)
        assertTrue { result.size == 5 }
    }

    @Test
    fun `two full week should contain 10 work units`() {
        val start = LocalDate.parse("2020-03-16")
        val end = LocalDate.parse("2020-03-30")
        val schedule = WorkloadScheduleDto(start, end, monday = 8, tuesday = 8, wednesday = 8, thursday = 8, friday = 8)
        val result = useCase.execute(schedule, employee)
        assertTrue { result.size == 10 }
    }

    @Test
    fun `should not contain holidays`() {
        val holiday = Holiday(1L, "", "", startDate = LocalDate.parse("2020-03-20"))
        whenever(getHolidaysUseCase.execute(Year.parse("2020"))).then { listOf(holiday) }
        val start = LocalDate.parse("2020-03-16")
        val end = LocalDate.parse("2020-03-23")
        val schedule = WorkloadScheduleDto(start, end, monday = 8, tuesday = 8, wednesday = 8, thursday = 8, friday = 8)
        val result = useCase.execute(schedule, employee)
        assertTrue { result.size == 4 && result.find { it.date == holiday.startDate } == null }
    }
}