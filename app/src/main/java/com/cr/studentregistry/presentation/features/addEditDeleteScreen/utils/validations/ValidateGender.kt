package com.cr.studentregistry.presentation.features.addEditDeleteScreen.utils.validations

import com.cr.studentregistry.domain.models.Gender
import com.cr.studentregistry.presentation.features.addEditDeleteScreen.utils.ValidationResult

class ValidateGender {

    operator fun invoke (gender: Gender?) : ValidationResult {
        if(gender == null) {
            return ValidationResult(
                successful = false,
                errorMessage = "Please select Gender"
            )
        }

        return ValidationResult(
            successful = true
        )

    }

}