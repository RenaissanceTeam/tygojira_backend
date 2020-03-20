package ru.fors.workload.api.request.domain.entity

class NoEmployeeForWorkloadException(request: Long): Throwable("No employee specified for request='$request'")