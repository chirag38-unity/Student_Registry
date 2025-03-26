package com.cr.studentregistry.utils

//data class TextFieldState(
//    val text: String = "",
//    val hint: String = "",
//    val error : String? = null
//)

data class TextFieldState<out T> (
    val value : T,
    val hint: String = "",
    val error: String? = null
)