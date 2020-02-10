package ru.fors.activity.data.repo

import org.springframework.data.jpa.repository.JpaRepository
import ru.fors.entity.activity.Activity

interface ActivityRepo : JpaRepository<Activity, Long>