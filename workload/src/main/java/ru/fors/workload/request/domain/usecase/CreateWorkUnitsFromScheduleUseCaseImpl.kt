package ru.fors.workload.request.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.entity.employee.Employee
import ru.fors.entity.holiday.Holiday
import ru.fors.entity.workload.WorkUnit
import ru.fors.production.calendar.api.domain.usecase.GetHolidaysUseCase
import ru.fors.workload.api.request.domain.dto.WorkloadScheduleDto
import ru.fors.workload.api.request.domain.entity.IllegalScheduleException
import ru.fors.workload.api.request.domain.usecase.CreateWorkUnitsFromScheduleUseCase
import java.time.DayOfWeek
import java.time.LocalDate

@Component
class CreateWorkUnitsFromScheduleUseCaseImpl(
        private val getHolidaysUseCase: GetHolidaysUseCase
) : CreateWorkUnitsFromScheduleUseCase {
    override fun execute(schedule: WorkloadScheduleDto, employee: Employee?): List<WorkUnit> {
        if (schedule.start > schedule.end) throw IllegalScheduleException("Start is later than end")
        val holidays = getHolidaysUseCase.execute()

        var day = schedule.start
        var units = mutableListOf<WorkUnit>()
        do {
            val scheduledHours = getHoursInDay(day, schedule)
            val isNotHoliday = holidays.find { it.onDay(day) } == null

            if (scheduledHours != null && isNotHoliday) units.add(WorkUnit(date = day, hours = scheduledHours))

            day = day.plusDays(1)
        } while (day < schedule.end)

        return units
    }

    private fun getHoursInDay(day: LocalDate, schedule: WorkloadScheduleDto): Int? {
        return when (day.dayOfWeek) {
            DayOfWeek.MONDAY -> schedule.monday
            DayOfWeek.TUESDAY -> schedule.tuesday
            DayOfWeek.WEDNESDAY -> schedule.wednesday
            DayOfWeek.THURSDAY -> schedule.thursday
            DayOfWeek.FRIDAY -> schedule.friday
            else -> null
        }?.apply { if (this == 0) return null }
    }

    private fun Holiday.onDay(day: LocalDate): Boolean {
        return day in startDate..endDate
    }

}