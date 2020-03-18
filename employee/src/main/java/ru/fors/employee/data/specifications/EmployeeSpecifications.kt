package ru.fors.employee.data.specifications

import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.domain.Specification.where
import ru.fors.employee.api.domain.entity.EmployeeFilter
import ru.fors.entity.activity.Activity
import ru.fors.entity.employee.Employee

fun EmployeeFilter.toSpecification(): Specification<Employee> {
    return where(firstName?.let(::firstNameSpecification))
//            .and(activities?.let(::activitiesSpecification))
            .and(middleName?.let(::middleNameSpecification))
            .and(lastName?.let(::lastNameSpecification))
            .and(position?.let(::positionSpecification))
            .and(subdivision?.let(::subdivisionSpecification))
            .and(skills.takeIf { it.isNotEmpty() }?.let(::skillsSpecification))
}

fun firstNameSpecification(name: String) = Specification<Employee> { root, query, criteriaBuilder ->
    criteriaBuilder.like(root.get<String>("firstName"), "%$name%")
}

fun middleNameSpecification(name: String) = Specification<Employee> { root, query, criteriaBuilder ->
    criteriaBuilder.like(root.get<String>("middleName"), "%$name%")
}

fun lastNameSpecification(name: String) = Specification<Employee> { root, query, criteriaBuilder ->
    criteriaBuilder.like(root.get<String>("lastName"), "%$name%")
}

fun positionSpecification(position: String) = Specification<Employee> { root, query, criteriaBuilder ->
    criteriaBuilder.like(root.get<String>("position"), "%$position%")
}

fun subdivisionSpecification(subdivision: String) = Specification<Employee> { root, query, criteriaBuilder ->
    criteriaBuilder.like(root.get<String>("subdivision"), "%$subdivision%")
}

fun skillsSpecification(skills: List<String>) = Specification<Employee> { root, query, criteriaBuilder ->
    val entitySkills = root.get<List<String>>("skills")

    val predicates = skills.map {
        criteriaBuilder.isMember(it, entitySkills)
    }.toTypedArray()

    query.where(*predicates).restriction
}

fun activitiesSpecification(activities: List<Activity>) = Specification<Employee> { root, query, criteriaBuilder ->
    //    select * from employee e
//    join workload w on e.id=w.employee_id
//    join activity_workload_workloads aws on w.id=aws.workloads_id
//    join activity_workload aw on aws.activity_workload_id = aw.id
//    where aw.activity_id in (121)


    TODO(" I DON'T FUCKING KNOW HOW TO MAKE THIS WORK!!, the above sql is right though")
//    val employeeActivities = query.subquery(ActivityWorkload::class.java)
//    val subRoot = employeeActivities.from(ActivityWorkload::class.java)
//
//
//    val list = subRoot.joinList<ActivityWorkload, Workload>("workloads")
//            .join<Workload, Employee>("employee")
//
//    employeeActivities.correlate(list)
//
//    val predicates = activities.map {
//        criteriaBuilder.equal(subRoot.get<Activity>("activity"), it)
//    }.toTypedArray()
//
//    query.where(*predicates).restriction
//
//    criteriaBuilder.all(employeeActivities).`in`(activities)
}
