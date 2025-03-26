package com.cr.studentregistry.presentation.features.addEditDeleteScreen.utils.validations

import com.cr.studentregistry.presentation.features.addEditDeleteScreen.utils.ValidationResult

class ValidateName {
    operator fun invoke (title: String) : ValidationResult {
        if(title.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Please insert name"
            )
        }

        return ValidationResult(
            successful = true
        )

    }
}