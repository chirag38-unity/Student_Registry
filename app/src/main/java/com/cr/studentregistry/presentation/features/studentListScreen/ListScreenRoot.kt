package com.cr.studentregistry.presentation.features.studentListScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.cr.studentregistry.domain.models.Student
import com.cr.studentregistry.presentation.features.studentListScreen.components.StudentGridItem
import com.cr.studentregistry.utils.ContentDisplayState
import com.cr.studentregistry.utils.DataError
import com.cr.studentregistry.utils.DataResultState
import com.cr.studentregistry.utils.toString
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicatorDefaults
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListScreenRoot(
    viewModel: ListScreenViewModel = koinViewModel<ListScreenViewModel>(),
    addNewStudent : () -> Unit = {},
    editStudent : (student : Student) -> Unit = {}
) {

    val studentsState by viewModel.studentsState.collectAsStateWithLifecycle()

    ListScreenContent(
        studentsState = studentsState,
        addNewStudent = addNewStudent,
        editStudent = editStudent,
        refreshData = viewModel::refreshData,
        retry = viewModel::retry
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListScreenContent(
    studentsState : DataResultState<List<Student>>,
    addNewStudent : () -> Unit = {},
    editStudent : (student : Student) -> Unit = {},
    refreshData : () -> Unit = {},
    retry : () -> Unit = {}
) {

    when(studentsState) {
        DataResultState.Idle, DataResultState.Loading -> StudentsListScreenLoadState()

        is DataResultState.Success -> ListScreenContent(
            students = studentsState.data,
            contentDisplayState = studentsState.contentDisplayState,
            addNewStudent = addNewStudent,
            editStudent = editStudent,
            refreshData = refreshData,
        )

        is DataResultState.Error -> StudentsListErrorScreen(
            error = studentsState.error,
            retry = retry
        )
    }

}

@Composable
private fun StudentsListScreenLoadState() {

    Box (
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(30.dp)
        )
    }

}

@Composable
private fun StudentsListErrorScreen(
    error: DataError,
    retry: () -> Unit
) {

    Scaffold (
        modifier = Modifier
            .fillMaxSize(),
    ) { paddingValues ->

        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = error.toString(LocalContext.current),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = retry,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                shape = RectangleShape
            ) {
                Text(
                    text = "Retry" ,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListScreenContent(
    students : List<Student>,
    contentDisplayState: ContentDisplayState? = null,
    addNewStudent : () -> Unit = {},
    editStudent : (student : Student) -> Unit = {},
    refreshData : () -> Unit = {},
) {

    val pullRefreshState = rememberPullRefreshState(
        refreshing = contentDisplayState == ContentDisplayState.Refreshing,
        onRefresh = refreshData
    )
    val coroutineScope = rememberCoroutineScope()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Students",
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = addNewStudent
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Student"
                )
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .padding(paddingValues)
                .pullRefresh(pullRefreshState)
        ) {

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 300.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = students,
                    key = { student -> student.id!! }
                ) { student ->
                    StudentGridItem(
                        student = student,
                        onClick = { editStudent(student) }
                    )
                }

            }

            PullRefreshIndicator(
                refreshing = contentDisplayState == ContentDisplayState.Refreshing,
                state = pullRefreshState,
                colors = PullRefreshIndicatorDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier
                    .align(Alignment.TopCenter)
            )

        }

    }

}