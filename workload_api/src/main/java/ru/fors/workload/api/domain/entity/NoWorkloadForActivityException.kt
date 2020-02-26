package ru.fors.workload.api.domain.entity

class NoWorkloadForActivityException(id: Long) : Throwable("No workload found for activity $id")