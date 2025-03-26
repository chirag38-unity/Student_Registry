package com.cr.studentregistry.presentation.features.addEditDeleteScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cr.studentregistry.domain.models.Gender
import com.cr.studentregistry.ui.components.CurrencyVisualTransformation
import com.cr.studentregistry.ui.components.PhoneNumberVisualTransformation
import com.cr.studentregistry.utils.TextFieldState
import com.cr.studentregistry.utils.clearFocusOnKeyboardDismiss
import com.cr.studentregistry.utils.formatDate
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun AddEditDeleteScreenRoot(
    studentId: String? = null,
    onBack: () -> Unit = {},
    viewModel: AddEditDeleteViewModel = koinViewModel()
) {

    val nameState by viewModel.nameState.collectAsStateWithLifecycle()
    val genderState by viewModel.genderState.collectAsStateWithLifecycle()
    val dobState by viewModel.dobState.collectAsStateWithLifecycle()
    val mobileState by viewModel.mobileState.collectAsStateWithLifecycle()
    val emailState by viewModel.emailState.collectAsStateWithLifecycle()
    val addressState by viewModel.addressState.collectAsStateWithLifecycle()
    val registrationFeeState by viewModel.registrationFeeState.collectAsStateWithLifecycle()
    val collegeFeeState by viewModel.collegeFeeState.collectAsStateWithLifecycle()
    val examFeeState by viewModel.examFeeState.collectAsStateWithLifecycle()
    val totalFeeState by viewModel.totalFeeState.collectAsStateWithLifecycle()
    val passingYearState by viewModel.passingYearState.collectAsStateWithLifecycle()

    LaunchedEffect (
        key1 = studentId
    ) {
        if (studentId != null) {
            viewModel.processExistingStudent(studentId)
        }
    }

    LaunchedEffect (
        Unit
    ) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                AddEditDeleteUIEvent.CloseScreen -> onBack()
            }
        }
    }

    AddEditDeleteScreenContent(
        studentId = studentId,
        nameState = nameState,
        genderState = genderState,
        dobState = dobState,
        mobileState = mobileState,
        emailState = emailState,
        addressState = addressState,
        registrationFeeState = registrationFeeState,
        collegeFeeState = collegeFeeState,
        examFeeState = examFeeState,
        totalFeeState = totalFeeState,
        passingYearState = passingYearState,
        sendEvent = viewModel::processEvent
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditDeleteScreenContent(
    nameState: TextFieldState<String>,
    genderState: TextFieldState<Gender?>,
    dobState: TextFieldState<Long?>,
    emailState: TextFieldState<String>,
    mobileState: TextFieldState<String>,
    addressState: TextFieldState<String>,
    registrationFeeState: TextFieldState<String>,
    collegeFeeState: TextFieldState<String>,
    examFeeState: TextFieldState<String>,
    totalFeeState: TextFieldState<String>,
    passingYearState: TextFieldState<String>,
    sendEvent: (AddEditDeleteEvents) -> Unit,
    studentId: String?,
) {

    var expanded by rememberSaveable { mutableStateOf(false) }
    var openDatePicker by rememberSaveable() { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = LocalDate.now().minusYears(18).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
        yearRange = 1900.. LocalDate.now().minusYears(18).year
    )

    val passingYears = (2024..2030).map { it.toString() }

    val focusManager = LocalFocusManager.current

    val nameFocusRequester = remember { FocusRequester() }
    val genderFocusRequester = remember { FocusRequester() }
    val dobFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }
    val mobileFocusRequester = remember { FocusRequester() }
    val addressFocusRequester = remember { FocusRequester() }
    val registrationFocusRequester = remember { FocusRequester() }
    val collegeFocusRequester = remember { FocusRequester() }
    val examFocusRequester = remember { FocusRequester() }
    val passingFocusRequester = remember { FocusRequester() }

    Scaffold (
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 300.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .consumeWindowInsets(PaddingValues())
                .padding(paddingValues)
        ) {

            // Name
            item (
                contentType = "Name"
            ) {
                Column {
                    OutlinedTextField(
                        value = nameState.value,
                        onValueChange = { sendEvent(AddEditDeleteEvents.EnteredName(it)) },
                        label = { Text(text = "Name") },
                        isError = !nameState.error.isNullOrEmpty(),
                        placeholder = { Text(text = nameState.hint) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next,
                            showKeyboardOnFocus = true,
                            capitalization = KeyboardCapitalization.Words
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Next)
                                genderFocusRequester.requestFocus()
                            }
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                            .clearFocusOnKeyboardDismiss()
                            .focusRequester(nameFocusRequester)
                             
                    )
                    if (nameState.error != null) {
                        Text(
                            text = nameState.error ?: "",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.End)
                        )
                    }
                }

            }

            // Gender
            item (
                contentType = "Gender"
            ) {
                Column (
                    modifier = Modifier.fillMaxWidth().clearFocusOnKeyboardDismiss()
                            .focusRequester(genderFocusRequester) ,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Gender", modifier = Modifier.fillMaxWidth())
                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Gender.entries.forEach {
                            RadioButton(
                                selected = genderState.value == it,
                                onClick = {
                                    sendEvent(AddEditDeleteEvents.EnteredGender(it))
                                }
                            )
                            Text(text = it.name)
                        }
                    }
                    if (genderState.error != null) {
                        Text(
                            text = genderState.error ?: "",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.End)
                        )
                    }

                }
            }

            // DOB
            item (
                contentType = "DOB"
            ) {
                Column {
                    OutlinedTextField(
                        value = dobState.value?.formatDate() ?: "",
                        onValueChange = {},
                        label = { Text(text = "Date of Birth") },
                        isError = dobState.error != null,
                        placeholder = { Text(text = dobState.hint) },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    openDatePicker = true
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CalendarMonth,
                                    contentDescription = "Select Birth Date"
                                )
                            }
                        },
                        readOnly = true,
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth() .clearFocusOnKeyboardDismiss()
                            .focusRequester(dobFocusRequester)
                             
                    )
                    if (dobState.error != null) {
                        Text(
                            text = dobState.error ?: "",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.End)
                        )
                    }
                }
            }

            // Email ID
            item (
                contentType = "Email"
            ) {
                Column {
                    OutlinedTextField(
                        value = emailState.value,
                        onValueChange = {
                            sendEvent(AddEditDeleteEvents.EnteredEmail(it))
                        },
                        label = { Text(text = "Email ID") },
                        placeholder = { Text(text = emailState.hint) },
                        isError = emailState.error != null,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next,
                            showKeyboardOnFocus = true,
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                mobileFocusRequester.requestFocus()
                            }
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth() .clearFocusOnKeyboardDismiss()
                            .focusRequester(emailFocusRequester)
                             
                    )
                    if (emailState.error != null) {
                        Text(
                            text = emailState.error ?: "",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.End)
                        )
                    }
                }

            }

            // Mobile
            item (
                contentType = "Mobile"
            ) {
                Column {
                    OutlinedTextField(
                        value = mobileState.value,
                        onValueChange = {
                            val digitsOnly = it.filter { char -> char.isDigit() } // Keep only numbers
                            if (digitsOnly.length <= 10) {
                                sendEvent(AddEditDeleteEvents.EnteredMobile(digitsOnly))
                            }
                        },
                        label = { Text(text = "Mobile Number") },
                        placeholder = { Text(text = mobileState.hint) },
                        isError = mobileState.error != null,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Next,
                            showKeyboardOnFocus = true,
                        ),
                        visualTransformation = PhoneNumberVisualTransformation(),
                        prefix = {
                            Text( text = "+91" )
                        },
                        singleLine = true,
                        keyboardActions = KeyboardActions(
                            onNext = {
                                addressFocusRequester.requestFocus()
                            }
                        ),
                        modifier = Modifier.fillMaxWidth() .clearFocusOnKeyboardDismiss()
                            .focusRequester(mobileFocusRequester)
                             
                    )
                    if (mobileState.error != null) {
                        Text(
                            text = mobileState.error ?: "",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.End)
                        )
                    }
                }

            }

            // Address
            item (
                contentType = "Address"
            ) {
                Column {
                    OutlinedTextField(
                        value = addressState.value,
                        onValueChange = {
                            sendEvent(AddEditDeleteEvents.EnteredAddress(it))
                        },
                        label = { Text(text = "Address") },
                        placeholder = { Text(text = addressState.hint) },
                        isError = addressState.error != null,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next,
                            showKeyboardOnFocus = true,
                            capitalization = KeyboardCapitalization.Words
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                registrationFocusRequester.requestFocus()
                            }
                        ),
                        modifier = Modifier.fillMaxWidth() .clearFocusOnKeyboardDismiss()
                            .focusRequester(addressFocusRequester)
                             
                    )
                    if (addressState.error != null) {
                        Text(
                            text = addressState.error ?: "",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.End)
                        )
                    }
                }

            }

            // Registration
            item (
                contentType = "Registration_Fee"
            ) {
                Column {
                    OutlinedTextField(
                        value = registrationFeeState.value,
                        onValueChange = {
                            val digitsOnly = it.filter { char -> char.isDigit() } // Keep only numbers
                            if (digitsOnly.length <= 10) {
                                sendEvent(AddEditDeleteEvents.EnteredRegistrationFee(digitsOnly))
                            }
                        },
                        label = { Text(text = "Registration Fee") },
                        placeholder = { Text(text = registrationFeeState.hint) },
                        isError = registrationFeeState.error != null,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next,
                            showKeyboardOnFocus = true,
                        ),
                        singleLine = true,
                        prefix = {
                            Text( text = "Rs." )
                        },
                        visualTransformation = CurrencyVisualTransformation(),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                collegeFocusRequester.requestFocus()
                            }
                        ),
                        modifier = Modifier.fillMaxWidth() .clearFocusOnKeyboardDismiss()
                            .focusRequester(registrationFocusRequester)
                             
                    )
                    if (registrationFeeState.error != null) {
                        Text(
                            text = registrationFeeState.error ?: "",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.End)
                        )
                    }
                }

            }

            // College
            item (
                contentType = "College_Fee"
            ) {
                Column {
                    OutlinedTextField(
                        value = collegeFeeState.value,
                        onValueChange = {
                            val digitsOnly = it.filter { char -> char.isDigit() } // Keep only numbers
                            if (digitsOnly.length <= 10) {
                                sendEvent(AddEditDeleteEvents.EnteredCollegeFee(digitsOnly))
                            }
                        },
                        label = { Text(text = "College Fee") },
                        isError = collegeFeeState.error != null,
                        placeholder = { Text(text = collegeFeeState.hint) },
                        prefix = {
                            Text( text = "Rs." )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next,
                            showKeyboardOnFocus = true,
                        ),
                        visualTransformation = CurrencyVisualTransformation(),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                examFocusRequester.requestFocus()
                            }
                        ),
                        modifier = Modifier.fillMaxWidth() .clearFocusOnKeyboardDismiss()
                            .focusRequester(collegeFocusRequester)
                             
                    )
                    if (collegeFeeState.error != null) {
                        Text(
                            text = collegeFeeState.error ?: "",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.End)
                        )
                    }
                }

            }

            // Exam
            item (
                contentType = "Exam_Fee"
            ) {
                Column {
                    OutlinedTextField(
                        value = examFeeState.value,
                        onValueChange = {
                            val digitsOnly = it.filter { char -> char.isDigit() } // Keep only numbers
                            if (digitsOnly.length <= 10) {
                                sendEvent(AddEditDeleteEvents.EnteredExamFee(digitsOnly))
                            }
                        },
                        label = { Text(text = "Exam Fee") },
                        isError = examFeeState.error != null,
                        placeholder = { Text(text = examFeeState.hint) },
                        prefix = {
                            Text( text = "Rs." )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done,
                            showKeyboardOnFocus = true,
                        ),
                        visualTransformation = CurrencyVisualTransformation(),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                passingFocusRequester.requestFocus()
                            }
                        ),
                        modifier = Modifier.fillMaxWidth() .clearFocusOnKeyboardDismiss()
                            .focusRequester(examFocusRequester)
                             
                    )
                    if (examFeeState.error != null) {
                        Text(
                            text = examFeeState.error ?: "",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.End)
                        )
                    }
                }

            }

            // Total
            item (
                contentType = "Total_Fee"
            ) {
                Column {
                    OutlinedTextField(
                        value = totalFeeState.value,
                        onValueChange = {},
                        label = { Text(text = "Total Fee") },
                        placeholder = { Text(text = totalFeeState.hint) },
                        isError = totalFeeState.error != null,
                        prefix = {
                            Text( text = "Rs." )
                        },
                        singleLine = true,
                        readOnly = false,
                        visualTransformation = CurrencyVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (totalFeeState.error != null) {
                        Text(
                            text = totalFeeState.error ?: "",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.End)
                        )
                    }
                }

            }

            item (
                contentType = "Passing_Year"
            ) {
                Column {
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = passingYearState.value,
                            onValueChange = {},
                            label = { Text("Passing Year") },
                            isError = passingYearState.error != null,
                            readOnly = true,
                            singleLine = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expanded
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                 .clearFocusOnKeyboardDismiss()
                            .focusRequester(passingFocusRequester)
                                .menuAnchor(MenuAnchorType.PrimaryEditable, true)
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            passingYears.forEach { year ->
                                DropdownMenuItem(
                                    text = { Text(year) },
                                    onClick = {
                                        sendEvent(AddEditDeleteEvents.EnteredPassingYear(year))
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                    if (passingYearState.error != null) {
                        Text(
                            text = passingYearState.error ?: "",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.End)
                        )
                    }
                }


            }

            item (
                contentType = "Submit Button",
                span = { GridItemSpan(maxLineSpan) }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = { sendEvent(AddEditDeleteEvents.SaveStudent) },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = if (studentId!= null) "Update" else "Save")
                    }

                    Button(
                        onClick = { sendEvent(AddEditDeleteEvents.DeleteStudent) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error,
                            contentColor = MaterialTheme.colorScheme.onError
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = if (studentId!= null) "Delete" else "Cancel")
                    }
                }
            }

        }

        if (openDatePicker) {

            DatePickerDialog(
                onDismissRequest = { openDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        openDatePicker = false
                        datePickerState.selectedDateMillis?.let {
                            Napier.d("Selected date $it")
                            sendEvent(AddEditDeleteEvents.EnteredDob(it))
                        }
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { openDatePicker = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState,
                )
            }
        }

    }

}