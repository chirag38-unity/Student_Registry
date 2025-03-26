package com.cr.studentregistry.presentation.features.addEditDeleteScreen.utils.validations

import com.cr.studentregistry.presentation.features.addEditDeleteScreen.utils.ValidationResult

class ValidatePassingYear {
    operator fun invoke(year: String): ValidationResult {
        if (year.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Please select passing year"
            )
        }

        return ValidationResult(successful = true)
    }
}