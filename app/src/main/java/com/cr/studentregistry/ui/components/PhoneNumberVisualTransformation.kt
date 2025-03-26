package com.cr.studentregistry.ui.components

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneNumberVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val raw = text.text.filter { it.isDigit() } // Ensure only digits are processed
        val formatted = buildString {
            for (i in raw.indices) {
                if (i == 5) append(" ") // Add space after 5 digits
                append(raw[i])
            }
        }

        return TransformedText(AnnotatedString(formatted), offsetTranslator)
    }

    private val offsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return if (offset <= 5) offset else offset + 1
        }

        override fun transformedToOriginal(offset: Int): Int {
            return if (offset <= 5) offset else offset - 1
        }
    }
}