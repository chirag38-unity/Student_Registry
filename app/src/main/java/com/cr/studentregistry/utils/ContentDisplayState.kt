package com.cr.studentregistry.utils

/**
 * Represents different states of content display in a paginated list.
 */
sealed interface ContentDisplayState {
    data object Refreshing : ContentDisplayState
    data object Paginating : ContentDisplayState
    data class PaginationError(val error: DataError) : ContentDisplayState
}