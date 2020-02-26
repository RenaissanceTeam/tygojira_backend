package ru.fors.entity.workload

data class Conflict(
        val workUnit: WorkUnit,
        val with: ConflictedWork
)
