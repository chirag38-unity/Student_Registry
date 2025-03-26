package com.cr.studentregistry.domain.usecase

import com.cr.studentregistry.data.repository.StudentsRepository
import com.cr.studentregistry.domain.models.Student
import com.cr.studentregistry.utils.DataError
import com.cr.studentregistry.utils.Result

class GetStudent constructor (
    private val repository: StudentsRepository
) {

    suspend operator fun invoke (
        id: String
    ) : Result<Student, DataError> = repository.getStudent(id)

}