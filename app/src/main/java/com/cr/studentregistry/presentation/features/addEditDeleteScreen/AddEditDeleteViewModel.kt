package com.cr.studentregistry.presentation.features.addEditDeleteScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cr.studentregistry.domain.models.Gender
import com.cr.studentregistry.domain.models.Student
import com.cr.studentregistry.domain.usecase.StudentsUseCase
import com.cr.studentregistry.presentation.features.addEditDeleteScreen.utils.StudentFormValidationUseCase
import com.cr.studentregistry.utils.TextFieldState
import com.cr.studentregistry.utils.onSuccess
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddEditDeleteViewModel constructor(
    private val studentsUseCase: StudentsUseCase,
    private val validationUseCase: StudentFormValidationUseCase,
) : ViewModel() {

    private val _nameState = MutableStateFlow(TextFieldState(value = ""))
    val nameState = _nameState.asStateFlow()

    private val _genderState = MutableStateFlow<TextFieldState<Gender?>>(TextFieldState(value = null))
    val genderState = _genderState.asStateFlow()

    private val _dobState = MutableStateFlow(TextFieldState<Long?>(value = null))
    val dobState = _dobState.asStateFlow()

    private val _emailState = MutableStateFlow(TextFieldState(value = ""))
    val emailState = _emailState.asStateFlow()

    private val _mobileState = MutableStateFlow(TextFieldState(value = ""))
    val mobileState = _mobileState.asStateFlow()

    private val _addressState = MutableStateFlow(TextFieldState(value = ""))
    val addressState = _addressState.asStateFlow()

    private val _registrationFeeState = MutableStateFlow(TextFieldState(value = ""))
    val registrationFeeState = _registrationFeeState.asStateFlow()

    private val _collegeFeeState = MutableStateFlow(TextFieldState(value = ""))
    val collegeFeeState = _collegeFeeState.asStateFlow()

    private val _examFeeState = MutableStateFlow(TextFieldState(value = ""))
    val examFeeState = _examFeeState.asStateFlow()

    private val _totalFeeState = MutableStateFlow(TextFieldState(value = ""))
    val totalFeeState = _totalFeeState
        .onStart { observeFees() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = TextFieldState(value = "")
        )

    private val _passingYearState = MutableStateFlow(TextFieldState(value = ""))
    val passingYearState = _passingYearState.asStateFlow()

    private var studentId: String? = null

    private val _eventFlow = MutableSharedFlow<AddEditDeleteUIEvent>(
        replay = 0,                 // No replayed events (default)
        extraBufferCapacity = 1,    // Allows buffering of one extra event
        onBufferOverflow = BufferOverflow.DROP_OLDEST // Drops oldest event if buffer overflows
    )
    val eventFlow = _eventFlow.asSharedFlow()

    private fun observeFees() {
        viewModelScope.launch {
            combine(
                _registrationFeeState,
                _collegeFeeState,
                _examFeeState
            ) { regFee, colFee, examFee ->
                val total = (regFee.value.toIntOrNull() ?: 0) +
                        (colFee.value.toIntOrNull() ?: 0) +
                        (examFee.value.toIntOrNull() ?: 0)
                TextFieldState(value = total.toString())
            }.collect { updatedTotal ->
                _totalFeeState.update {
                    updatedTotal
                }
            }
        }
    }

    fun processEvent(event: AddEditDeleteEvents) {
        when (event) {
            is AddEditDeleteEvents.EnteredName -> {
                _nameState.update {
                    it.copy(
                        value = event.value,
                        error = null
                    )
                }
            }
            is AddEditDeleteEvents.EnteredGender -> {
                _genderState.update {
                    it.copy(value = event.value,error = null)
                }
            }
            is AddEditDeleteEvents.EnteredDob -> {
                _dobState.update {
                    it.copy(value = event.value,error = null)
                }
            }
            is AddEditDeleteEvents.EnteredMobile -> {
                _mobileState.update {
                    it.copy(value = event.value,error = null)
                }
            }
            is AddEditDeleteEvents.EnteredEmail -> {
               _emailState.update {
                   it.copy(value = event.value,error = null)
               }
            }
            is AddEditDeleteEvents.EnteredAddress -> {
                _addressState.update {
                    it.copy(value = event.value,error = null)
                }
            }

            is AddEditDeleteEvents.EnteredCollegeFee -> {
                _collegeFeeState.update {
                    it.copy(value = event.value,error = null)
                }
            }
            is AddEditDeleteEvents.EnteredRegistrationFee -> {
                _registrationFeeState.update {
                    it.copy(value = event.value,error = null)
                }
            }
            is AddEditDeleteEvents.EnteredExamFee -> {
                _examFeeState.update {
                    it.copy(value = event.value,error = null)
                }
            }

            is AddEditDeleteEvents.EnteredPassingYear -> {
                _passingYearState.update {
                    it.copy(value = event.value,error = null)
                }
            }

            AddEditDeleteEvents.SaveStudent -> {
                viewModelScope.launch {
                    validateFields {
                        if (studentId != null) {
                            viewModelScope.launch {
                                studentsUseCase.updateStudent(
                                    Student(
                                        id = studentId,
                                        name = _nameState.value.value,
                                        dateOfBirth = _dobState.value.value!!,
                                        email = _emailState.value.value,
                                        phoneNumber = _mobileState.value.value,
                                        gender = _genderState.value.value!!,
                                        address = _addressState.value.value,
                                        passingYear = _passingYearState.value.value,
                                        registrationFees = _registrationFeeState.value.value.toInt(),
                                        examinationFees = _examFeeState.value.value.toInt(),
                                        collegeFees = _collegeFeeState.value.value.toInt()
                                    )
                                )
                            }
                        } else {
                            viewModelScope.launch {
                                studentsUseCase.addStudent(
                                    Student(
                                        id = studentId,
                                        name = _nameState.value.value,
                                        dateOfBirth = _dobState.value.value!!,
                                        email = _emailState.value.value,
                                        phoneNumber = _mobileState.value.value,
                                        gender = _genderState.value.value!!,
                                        address = _addressState.value.value,
                                        passingYear = _passingYearState.value.value,
                                        registrationFees = _registrationFeeState.value.value.toInt(),
                                        examinationFees = _examFeeState.value.value.toInt(),
                                        collegeFees = _collegeFeeState.value.value.toInt()
                                    )
                                )
                            }
                        }
                        viewModelScope.launch {
                            _eventFlow.emit(AddEditDeleteUIEvent.CloseScreen)
                        }
                    }
                }
            }
            AddEditDeleteEvents.DeleteStudent -> {
                viewModelScope.launch {
                    studentId?.let {
                        studentsUseCase.deleteStudent(it)
                    }
                    viewModelScope.launch {
                        _eventFlow.emit(AddEditDeleteUIEvent.CloseScreen)
                    }
                }
            }
        }

    }

    fun processExistingStudent(id: String) {

        viewModelScope.launch {
            studentId = id

            studentsUseCase.getStudent(id)
                .onSuccess { student ->
                    viewModelScope.launch {
                        _nameState.update {
                            it.copy(value = student.name)
                        }
                        _genderState.update {
                            it.copy(value = student.gender)
                        }
                        _dobState.update {
                            it.copy(value = student.dateOfBirth)
                        }
                        _emailState.update {
                            it.copy(value = student.email)
                        }
                        _mobileState.update {
                            it.copy(value = student.phoneNumber)
                        }
                        _addressState.update {
                            it.copy(value = student.address)
                        }
                        _registrationFeeState.update {
                            it.copy(value = student.registrationFees.toString())
                        }
                        _collegeFeeState.update {
                            it.copy(value = student.collegeFees.toString())
                        }
                        _examFeeState.update {
                            it.copy(value = student.examinationFees.toString())
                        }
                        _passingYearState.update {
                            it.copy(value = student.passingYear)
                        }
                    }

                }

        }

    }

    private fun validateFields( lambda : () -> Unit ) {
        val nameResult = validationUseCase.validateName(_nameState.value.value)
        val genderResult = validationUseCase.validateGender(_genderState.value.value)
        val dobResult = validationUseCase.validateDOB(_dobState.value.value)
        val emailIdResult = validationUseCase.validateEmail(_emailState.value.value)
        val mobileResult = validationUseCase.validatePhoneNumber(_mobileState.value.value)
        val addressResult = validationUseCase.validateAddress(_addressState.value.value)
        val registrationFeeResult = validationUseCase.validateFee("Registration",_registrationFeeState.value.value)
        val collegeFeeResult = validationUseCase.validateFee("College",_collegeFeeState.value.value)
        val examFeeResult = validationUseCase.validateFee("Exam",_examFeeState.value.value)
        val passingYearResult = validationUseCase.validatePassingYear(_passingYearState.value.value)

        val hasError = listOf(
            nameResult,
            genderResult,
            dobResult,
            emailIdResult,
            mobileResult,
            addressResult,
            registrationFeeResult,
            collegeFeeResult,
            examFeeResult,
            passingYearResult
        ).any { !it.successful }

        if(hasError) {
            _nameState.update {
                it.copy(error = nameResult.errorMessage)
            }
            _genderState.update {
                it.copy(error = genderResult.errorMessage)
            }
            _dobState.update {
                it.copy(error = dobResult.errorMessage)
            }
            _emailState.update {
                it.copy(error = emailIdResult.errorMessage)
            }
            _mobileState.update {
                it.copy(error = mobileResult.errorMessage)
            }
            _addressState.update {
                it.copy(error = addressResult.errorMessage)
            }
            _registrationFeeState.update {
                it.copy(error = registrationFeeResult.errorMessage)
            }
            _collegeFeeState.update {
                it.copy(error = collegeFeeResult.errorMessage)
            }
            _examFeeState.update {
                it.copy(error = examFeeResult.errorMessage)
            }
            _passingYearState.update {
                it.copy(error = passingYearResult.errorMessage)
            }

            return
        }

        lambda.invoke()

    }

}