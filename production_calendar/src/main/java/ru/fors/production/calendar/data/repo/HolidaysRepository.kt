package ru.fors.production.calendar.data.repo

import org.springframework.data.jpa.repository.JpaRepository
import ru.fors.production.calendar.api.domain.entity.Holiday
import java.time.LocalDate

interface HolidaysRepository : JpaRepository<Holiday, LocalDate>