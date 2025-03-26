package com.cr.studentregistry.presentation.features.addEditDeleteScreen.utils.validations

import com.cr.studentregistry.presentation.features.addEditDeleteScreen.utils.ValidationResult

class ValidateDOB {

    operator fun invoke (dob: Long?) : ValidationResult {
        if(dob == null) {
            return ValidationResult(
                successful = false,
                errorMessage = "Please insert date of birth"
            )
        }

        return ValidationResult(
            successful = true
        )

    }

}