package com.cr.studentregistry.di

import com.cr.studentregistry.presentation.features.addEditDeleteScreen.utils.StudentFormValidationUseCase
import com.cr.studentregistry.presentation.features.addEditDeleteScreen.utils.validations.ValidateAddress
import com.cr.studentregistry.presentation.features.addEditDeleteScreen.utils.validations.ValidateDOB
import com.cr.studentregistry.presentation.features.addEditDeleteScreen.utils.validations.ValidateEmailID
import com.cr.studentregistry.presentation.features.addEditDeleteScreen.utils.validations.ValidateFee
import com.cr.studentregistry.presentation.features.addEditDeleteScreen.utils.validations.ValidateGender
import com.cr.studentregistry.presentation.features.addEditDeleteScreen.utils.validations.ValidateName
import com.cr.studentregistry.presentation.features.addEditDeleteScreen.utils.validations.ValidatePassingYear
import com.cr.studentregistry.presentation.features.addEditDeleteScreen.utils.validations.ValidatePhoneNumber
import org.koin.dsl.module

val appModule = module {

    single <StudentFormValidationUseCase> {
        StudentFormValidationUseCase(
            validateName = ValidateName(),
            validateGender = ValidateGender(),
            validateDOB = ValidateDOB(),
            validateEmail = ValidateEmailID(),
            validatePhoneNumber = ValidatePhoneNumber(),
            validateAddress = ValidateAddress(),
            validateFee = ValidateFee(),
            validatePassingYear = ValidatePassingYear()
        )
    }

}