package ru.fors.production.calendar.data.repo

import org.springframework.data.jpa.repository.JpaRepository
import ru.fors.entity.holiday.Holiday
import java.time.LocalDate

interface HolidaysRepository : JpaRepository<Holiday, LocalDate>