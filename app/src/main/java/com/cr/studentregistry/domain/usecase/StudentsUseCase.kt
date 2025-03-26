package com.cr.studentregistry.domain.usecase

data class StudentsUseCase(
    val getStudentsList: GetStudentsList,
    val getStudent: GetStudent,
    val addStudent: AddStudent,
    val updateStudent: UpdateStudent,
    val deleteStudent: DeleteStudent
)
