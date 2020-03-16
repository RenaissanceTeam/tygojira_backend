package ru.fors.production.calendar.data.repo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.fors.entity.holiday.Holiday

interface HolidaysRepository : JpaRepository<Holiday, Long> {

    @Query("select h from Holiday h where year( h.startDate ) = ?1 or year( h.endDate ) = ?1")
    fun getHolidaysByYear(year: Int): ArrayList<Holiday>
}