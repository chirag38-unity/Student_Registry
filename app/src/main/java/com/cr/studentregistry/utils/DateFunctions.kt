package com.cr.studentregistry.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Long.formatDate(): String {
    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
        .format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
}