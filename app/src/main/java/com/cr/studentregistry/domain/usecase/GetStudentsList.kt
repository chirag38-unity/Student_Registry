package com.cr.studentregistry.domain.usecase

import com.cr.studentregistry.data.repository.StudentsRepository
import com.cr.studentregistry.domain.models.Student
import kotlinx.coroutines.flow.Flow

class GetStudentsList constructor (
    private val repository: StudentsRepository
) {

    suspend operator fun invoke (
    ) : Flow<List<Student>> = repository.getStudents()

}