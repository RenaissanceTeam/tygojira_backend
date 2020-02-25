package ru.fors.workload.api.request.domain.entity

import ru.fors.entity.MINIMUM_WORKLOAD_WORKING_HOURS
import ru.fors.entity.workload.WorkUnit

class WorkloadLessThanMinimumException(workUnit: WorkUnit)
    : Throwable("Provided workunit='$workUnit' has less than minimum hours='$MINIMUM_WORKLOAD_WORKING_HOURS'")