package ru.fors.workload.api.request.domain.entity

import ru.fors.entity.workload.request.WorkloadRequest

class NoInitiatorException(request: WorkloadRequest): Throwable("No initiator in $request")