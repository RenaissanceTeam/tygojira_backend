package ru.fors.employee.data.specifications

import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.domain.Specification.where
import ru.fors.employee.api.domain.entity.EmployeeFilter
import ru.fors.entity.employee.Employee

fun EmployeeFilter.toSpecification(): Specification<Employee> {
    return where(firstName?.let(::firstNameSpecification))
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
