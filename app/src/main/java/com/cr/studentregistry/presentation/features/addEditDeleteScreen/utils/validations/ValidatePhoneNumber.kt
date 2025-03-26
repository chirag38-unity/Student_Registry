package com.cr.studentregistry.presentation.features.addEditDeleteScreen.utils.validations

import com.cr.studentregistry.presentation.features.addEditDeleteScreen.utils.ValidationResult

class ValidatePhoneNumber {

    operator fun invoke(phoneNumber: String): ValidationResult {
        if (phoneNumber.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Please insert phone number"
            )
        }

        if (phoneNumber.length < 10) {
            return ValidationResult(
                successful = false,
                errorMessage = "Phone number must be at least 10 characters"
            )

        }

        return ValidationResult(successful = true)
    }
}