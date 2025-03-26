package com.cr.studentregistry.utils

import android.content.Context
import com.cr.studentregistry.R

interface GeneralError

/**
 * Represents different types of data-related errors.
 */
sealed interface DataError: GeneralError {

    /**
     * Represents different types of network-related errors.
     */
    enum class NetworkError : DataError {
        REQUEST_TIMEOUT,   // The request took too long to respond
        UNAUTHORIZED,      // Authentication failure
        CONFLICT,          // Resource conflict error
        TOO_MANY_REQUESTS, // Rate limiting error
        NO_INTERNET,       // No internet connection
        PAYLOAD_TOO_LARGE, // Request payload exceeds the limit
        SERVER_ERROR,      // Generic server-side error
        SERIALIZATION,     // Error parsing the response
        CANCELLED,         // Request was cancelled
        UNKNOWN;           // Unclassified error
    }

    /**
     * Represents different types of local storage or processing errors.
     */
    enum class Local: DataError {
        DISK_FULL,  // Insufficient disk space
        UNKNOWN     // Unclassified local error
    }
}

/**
 * Represents different types of application-related errors.
 */
sealed interface AppError : GeneralError {

    /**
     * Represents different types of authentication-related errors.
     */
    enum class AuthError : AppError {
        NO_GOOGLE_ACCOUNT,   // No Google account found
        NO_USER_DATA,        // No user data available
        INVALID_CREDENTIALS, // Incorrect login credentials
        CANCELLED,           // Operation cancelled by user
        UNKNOWN;             // Unclassified authentication error
    }

    /**
     * Represents different types of permission-related errors.
     */
    enum class PermissionError : AppError {
        NO_LOCATION_PERMISSION, // Missing location permission
        UNKNOWN;                // Unclassified permission error
    }

}

/**
 * Converts a [DataError] into a user-friendly string resource.
 *
 * @param context The application context used to fetch string resources.
 * @return A human-readable error message.
 */
fun DataError.toString(context: Context): String {
    val resId = when(this) {
        DataError.NetworkError.REQUEST_TIMEOUT -> R.string.error_request_timeout
        DataError.NetworkError.TOO_MANY_REQUESTS -> R.string.error_too_many_requests
        DataError.NetworkError.NO_INTERNET -> R.string.error_no_internet
        DataError.NetworkError.SERVER_ERROR -> R.string.error_server_unknown
        DataError.NetworkError.SERIALIZATION -> R.string.error_serialization
        DataError.NetworkError.UNKNOWN -> R.string.error_server_unknown
        DataError.NetworkError.UNAUTHORIZED -> R.string.unauthorised
        DataError.NetworkError.CONFLICT -> R.string.network_conflict
        DataError.NetworkError.PAYLOAD_TOO_LARGE -> R.string.payload_too_large
        DataError.NetworkError.CANCELLED -> R.string.request_cancelled
        DataError.Local.DISK_FULL -> R.string.disk_full
        DataError.Local.UNKNOWN -> R.string.error_local_unknown
    }
    return context.getString(resId)
}