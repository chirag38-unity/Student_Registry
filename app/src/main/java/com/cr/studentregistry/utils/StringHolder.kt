package com.cr.studentregistry.utils

import android.content.Context
import android.content.res.Resources
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource


/**
 * A sealed interface representing a types of strings making it available to the composable functions.
 */
sealed interface StringHolder {

    data class Value(val value: String) : StringHolder
    data class Resource(@StringRes val resId: Int) : StringHolder

    class ParametrizedResource(@StringRes val resId: Int, vararg val formatArgs: Any) : StringHolder
    class Plural(@PluralsRes val resId: Int, val count: Int, vararg val formatArgs: Any) : StringHolder
}

val StringHolder.value
    @Composable
    get() = when(this) {
        is StringHolder.Value -> value
        is StringHolder.Resource -> stringResource(resId)
        is StringHolder.ParametrizedResource -> stringResource(resId, formatArgs)
        is StringHolder.Plural -> pluralStringResource(resId, count, formatArgs)
    }

fun StringHolder.fromContext(context: Context) = fromResources(context.resources)

fun StringHolder.fromResources(resources: Resources) = when (this) {
    is StringHolder.Value -> value
    is StringHolder.Resource -> resources.getString(resId)
    is StringHolder.ParametrizedResource -> resources.getString(resId, *formatArgs)
    is StringHolder.Plural -> resources.getQuantityString(resId, count, *formatArgs)
}