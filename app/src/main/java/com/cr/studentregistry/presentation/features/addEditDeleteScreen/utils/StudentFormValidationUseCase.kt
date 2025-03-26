package com.cr.studentregistry.presentation.features.addEditDeleteScreen.utils

import com.cr.studentregistry.presentation.features.addEditDeleteScreen.utils.validations.ValidateAddress
import com.cr.studentregistry.presentation.features.addEditDeleteScreen.utils.validations.ValidateDOB
import com.cr.studentregistry.presentation.features.addEditDeleteScreen.utils.validations.ValidateEmailID
import com.cr.studentregistry.presentation.features.addEditDeleteScreen.utils.validations.ValidateFee
import com.cr.studentregistry.presentation.features.addEditDeleteScreen.utils.validations.ValidateGender
import com.cr.studentregistry.presentation.features.addEditDeleteScreen.utils.validations.ValidateName
import com.cr.studentregistry.presentation.features.addEditDeleteScreen.utils.validations.ValidatePassingYear
import com.cr.studentregistry.presentation.features.addEditDeleteScreen.utils.validations.ValidatePhoneNumber

data class StudentFormValidationUseCase (
    val validateName: ValidateName,
    val validateGender: ValidateGender,
    val validateDOB: ValidateDOB,
    val validateEmail: ValidateEmailID,
    val validatePhoneNumber: ValidatePhoneNumber,
    val validateAddress: ValidateAddress,
    val validateFee: ValidateFee,
    val validatePassingYear: ValidatePassingYear
)