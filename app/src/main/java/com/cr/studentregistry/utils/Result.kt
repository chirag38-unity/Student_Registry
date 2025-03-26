package com.cr.studentregistry.utils

/**
 * A sealed interface representing a result that can be either a success or an error.
 *
 * @param D The type of data in case of success.
 * @param E The type of error in case of failure, extending [GeneralError].
 */
sealed interface Result<out D, out E: GeneralError> {
    /**
     * Represents a successful result with associated data.
     *
     * @param data The successful data result.
     */
    data class Success<out D>(val data: D): Result<D, Nothing>

    /**
     * Represents an error result with an associated error.
     *
     * @param error The error information.
     */
    data class Error<out E: GeneralError>(val error: E): Result<Nothing, E>
}

/**
 * Transforms the successful result's data into another type while preserving errors.
 *
 * @param map A transformation function to convert data.
 * @return A [Result] containing transformed data or the same error.
 */
inline fun <T, E: GeneralError, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when(this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}

/**
 * Converts a result into an empty success or propagates the error.
 *
 * @return A [Result] with Unit as data or the same error.
 */
fun <T, E: GeneralError> Result<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map {  }
}

/**
 * Executes an action only if the result is successful.
 *
 * @param action The action to execute when success occurs.
 * @return The same result for chaining operations.
 */
inline fun <T, E: GeneralError> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    return when(this) {
        is Result.Error -> this
        is Result.Success -> {
            action(data)
            this
        }
    }
}

/**
 * Executes an action only if the result is an error.
 *
 * @param action The action to execute when an error occurs.
 * @return The same result for chaining operations.
 */
inline fun <T, E: GeneralError> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
    return when(this) {
        is Result.Error -> {
            action(error)
            this
        }
        is Result.Success -> this
    }
}

/**
 * A type alias for a result with Unit as success data, typically used when the success case does not need data.
 */
typealias EmptyResult<E> = Result<Unit, E>