package com.cr.studentregistry.di

import com.cr.studentregistry.data.repository.StudentsRepository
import com.cr.studentregistry.domain.usecase.AddStudent
import com.cr.studentregistry.domain.usecase.DeleteStudent
import com.cr.studentregistry.domain.usecase.GetStudent
import com.cr.studentregistry.domain.usecase.GetStudentsList
import com.cr.studentregistry.domain.usecase.StudentsUseCase
import com.cr.studentregistry.domain.usecase.UpdateStudent
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {

    single { StudentsRepository(get( named("students") )) }

    single {
        StudentsUseCase(
            getStudentsList = GetStudentsList(get()),
            getStudent = GetStudent(get()),
            addStudent = AddStudent(get()),
            updateStudent = UpdateStudent(get()),
            deleteStudent = DeleteStudent(get())
        )
    }

}