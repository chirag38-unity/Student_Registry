package com.cr.studentregistry.presentation.features.addEditDeleteScreen

sealed class AddEditDeleteUIEvent {
    data object CloseScreen: AddEditDeleteUIEvent()
}