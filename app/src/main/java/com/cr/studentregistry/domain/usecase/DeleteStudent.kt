package com.cr.studentregistry.domain.usecase

import com.cr.studentregistry.data.repository.StudentsRepository
import com.cr.studentregistry.domain.models.Student

class DeleteStudent (
    private val repository: StudentsRepository
) {

    suspend operator fun invoke (
        studentId: String
    ) = repository.deleteStudent(studentId)

}