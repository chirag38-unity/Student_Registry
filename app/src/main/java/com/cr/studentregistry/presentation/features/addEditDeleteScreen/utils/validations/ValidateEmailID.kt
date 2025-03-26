package com.cr.studentregistry.presentation.features.addEditDeleteScreen.utils.validations

import com.cr.studentregistry.presentation.features.addEditDeleteScreen.utils.ValidationResult

class ValidateEmailID {
    operator fun invoke (email: String) : ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Please insert email"
            )
        }

        val emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()

        if (!email.matches(emailPattern)) {
            return ValidationResult(
                successful = false,
                errorMessage = "Invalid email format"
            )
        }

        return ValidationResult(successful = true)

    }
}