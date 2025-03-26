package com.cr.studentregistry.presentation.features.addEditDeleteScreen.utils.validations

import com.cr.studentregistry.presentation.features.addEditDeleteScreen.utils.ValidationResult

class ValidateFee {
    operator fun invoke(name: String, fee: String): ValidationResult {
        if (fee.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Please insert $name fee"
            )
        }

        val feeValue = fee.toIntOrNull()
        if (feeValue == null || feeValue <= 0) {
            return ValidationResult(
                successful = false,
                errorMessage = "Invalid $name fee. Enter a valid number greater than zero."
            )
        }

        return ValidationResult(successful = true)
    }
}