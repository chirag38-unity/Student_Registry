package com.cr.studentregistry.di

import androidx.lifecycle.SavedStateHandle
import com.cr.studentregistry.presentation.features.addEditDeleteScreen.AddEditDeleteViewModel
import com.cr.studentregistry.presentation.features.studentListScreen.ListScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel <ListScreenViewModel> { (handle: SavedStateHandle) ->
        ListScreenViewModel(get())
    }

    viewModel <AddEditDeleteViewModel> { (handle: SavedStateHandle) ->
        AddEditDeleteViewModel(get(), get())
    }

}