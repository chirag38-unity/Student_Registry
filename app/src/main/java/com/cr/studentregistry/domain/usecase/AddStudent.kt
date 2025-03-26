package com.cr.studentregistry.domain.usecase

import com.cr.studentregistry.data.repository.StudentsRepository
import com.cr.studentregistry.domain.models.Student

class AddStudent constructor (
    private val repository: StudentsRepository
) {

    suspend operator fun invoke (
        student: Student
    ) = repository.addStudent(student)

}