package com.cr.studentregistry.presentation.features.addEditDeleteScreen

import com.cr.studentregistry.domain.models.Gender

sealed class AddEditDeleteEvents {
    data class EnteredName(val value: String): AddEditDeleteEvents()
    data class EnteredDob(val value: Long): AddEditDeleteEvents()
    data class EnteredGender(val value: Gender): AddEditDeleteEvents()
    data class EnteredMobile(val value: String): AddEditDeleteEvents()
    data class EnteredEmail(val value: String): AddEditDeleteEvents()
    data class EnteredAddress(val value: String): AddEditDeleteEvents()
    data class EnteredRegistrationFee(val value: String): AddEditDeleteEvents()
    data class EnteredExamFee(val value: String): AddEditDeleteEvents()
    data class EnteredCollegeFee(val value: String): AddEditDeleteEvents()
    data class EnteredPassingYear(val value: String): AddEditDeleteEvents()
    data object SaveStudent: AddEditDeleteEvents()
    data object DeleteStudent: AddEditDeleteEvents()
}