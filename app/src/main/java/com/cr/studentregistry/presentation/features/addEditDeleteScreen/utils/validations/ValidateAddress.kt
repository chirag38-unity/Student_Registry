package com.cr.studentregistry.presentation.features.addEditDeleteScreen.utils.validations

import com.cr.studentregistry.presentation.features.addEditDeleteScreen.utils.ValidationResult

class ValidateAddress {
    operator fun invoke (address: String) : ValidationResult {
        if(address.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Please insert address"
            )
        }

        return ValidationResult(
            successful = true
        )

    }
}