package com.cr.studentregistry.domain.models

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Keep
@Serializable
@Parcelize
data class Student(
    val id: String? = null,
    val name: String,
    val dateOfBirth: Long,
    val email: String,
    val phoneNumber: String,
    val gender: Gender,
    val address: String,
    val passingYear: String,
    val registrationFees : Int,
    val examinationFees : Int,
    val collegeFees : Int,
) : Parcelable

@Serializable
@Parcelize
enum class Gender : Parcelable {
    Male,
    Female
}

object GenderSerializer : KSerializer<Gender> {
    override val descriptor = PrimitiveSerialDescriptor("Gender", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Gender) {
        encoder.encodeString(value.name)  // Store enum as String in Firestore
    }

    override fun deserialize(decoder: Decoder): Gender {
        return try {
            Gender.valueOf(decoder.decodeString()) // Convert String back to Enum
        } catch (e: IllegalArgumentException) {
            Gender.Male  // Default value in case of invalid data
        }
    }
}
