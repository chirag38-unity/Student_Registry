package com.cr.studentregistry.data.models

import android.os.Parcelable
import androidx.annotation.Keep
import com.cr.studentregistry.domain.models.Gender
import com.cr.studentregistry.domain.models.GenderSerializer
import com.cr.studentregistry.domain.models.Student
import com.google.firebase.firestore.PropertyName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Keep
@Serializable
@Parcelize
data class StudentDTO(
    @get:PropertyName("id")
    @set:PropertyName("id")
    var id: String?,
    @get:PropertyName("name")
    val name: String,
    @get:PropertyName("dateOfBirth")
    val dateOfBirth: Long,
    @get:PropertyName("email")
    val email: String,
    @get:PropertyName("phoneNumber")
    val phoneNumber: String,

    @Serializable(with = GenderSerializer::class)
    @get:PropertyName("gender")
    val gender: Gender,
    @get:PropertyName("address")
    val address: String,
    @get:PropertyName("passingYear")
    val passingYear: String,
    @get:PropertyName("registrationFees")
    val registrationFees : Int,
    @get:PropertyName("examinationFees")
    val examinationFees : Int,
    @get:PropertyName("collegeFees")
    val collegeFees : Int,
) : Parcelable {
    constructor() : this("", "", 0L, "", "", Gender.Female, "", "", 0, 0, 0)
}

fun StudentDTO.toStudent() : Student =
    Student(
        id = this.id,
        name = this.name,
        dateOfBirth = this.dateOfBirth,
        email = this.email,
        phoneNumber = this.phoneNumber,
        gender = this.gender,
        address = this.address,
        passingYear = this.passingYear,
        registrationFees = this.registrationFees,
        examinationFees = this.examinationFees,
        collegeFees = this.collegeFees,
    )

fun Student.toStudentDTO() : StudentDTO =
    StudentDTO(
        id = this.id ?: "",
        name = this.name,
        dateOfBirth = this.dateOfBirth,
        email = this.email,
        phoneNumber = this.phoneNumber,
        gender = this.gender,
        address = this.address,
        passingYear = this.passingYear,
        registrationFees = this.registrationFees,
        examinationFees = this.examinationFees,
        collegeFees = this.collegeFees,
    )

