package com.cr.studentregistry.ui.components

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class CurrencyVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val rawText = text.text.filter { it.isDigit() } // Keep only digits
        val formattedText = formatCurrency(rawText) // Format as currency

        return TransformedText(AnnotatedString(formattedText), offsetTranslator(rawText, formattedText))
    }

    private fun formatCurrency(input: String): String {
        return input.reversed()
            .chunked(3) // Group into thousands
            .joinToString(",") // Add commas
            .reversed()
    }

    private fun offsetTranslator(original: String, transformed: String) = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            val commas = transformed.count { it == ',' }
            return offset + commas
        }

        override fun transformedToOriginal(offset: Int): Int {
            return offset - transformed.take(offset).count { it == ',' }
        }
    }
}