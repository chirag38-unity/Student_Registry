package com.cr.studentregistry.presentation.features.studentListScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cr.studentregistry.domain.models.Student
import com.cr.studentregistry.domain.usecase.StudentsUseCase
import com.cr.studentregistry.utils.DataResultState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListScreenViewModel constructor(
    private val studentsUseCase: StudentsUseCase,
) : ViewModel() {


    private val _studentsState = MutableStateFlow<DataResultState<List<Student>>>(DataResultState.Loading)
    val studentsState = _studentsState
        .onStart { getStudentsList() }
        .stateIn(
            scope =  viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue =  DataResultState.Idle
        )

    fun getStudentsList() {
        viewModelScope.launch {
            studentsUseCase.getStudentsList().collect { list ->
                _studentsState.update {
                    DataResultState.Success(list)
                }
            }
        }
    }

    fun refreshData() {

    }

    fun retry() {

    }

}