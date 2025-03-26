package com.cr.studentregistry.presentation.navigation

import com.cr.studentregistry.domain.models.Student
import kotlinx.serialization.Serializable

sealed interface NavRoute {

    @Serializable
    data object StudentsListScreen : NavRoute

    @Serializable
    data object AddStudent : NavRoute

    @Serializable
    data class EditStudent(val student: Student) : NavRoute

    @Serializable
    data class AddEditStudent(val studentId: String? = null) : NavRoute

}