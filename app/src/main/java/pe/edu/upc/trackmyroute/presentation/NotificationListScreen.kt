package pe.edu.upc.trackmyroute.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.trackmyroute.domain.Notification

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationListScreen(viewModel: NotificationListViewModel) {
    val title = viewModel.title.value
    val state = viewModel.state.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notification List") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchBar(
                title = title,
                onTitleChanged = viewModel::onTitleChange,
                onSearch = viewModel::searchNotificationById
            )
            when {
                state.isLoading -> {
                    CircularProgressIndicator()
                }

                state.error.isNotEmpty() -> {
                    Text(
                        text = state.error,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                    Button(
                        onClick = { viewModel.searchNotificationById() },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text("Retry")
                    }
                }
                state.notifications?.isEmpty() == true -> {
                    Text(
                        "No notifications found",
                        modifier = Modifier.padding(16.dp)
                    )
                }
                else -> {
                    NotificationList(
                        notifications = state.notifications ?: emptyList())
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    title: String,
    onTitleChanged: (String) -> Unit,
    onSearch: () -> Unit
) {
    OutlinedTextField(
        value = title,
        onValueChange = onTitleChanged,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = { Text("Search notifications") },
        trailingIcon = {
            IconButton(onClick = onSearch) {
                Icon(Icons.Default.Search, contentDescription = "Search")
            }
        },
        singleLine = true
    )
}

@Composable
fun NotificationList(notifications: List<Notification>?) {
    LazyColumn (
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (notifications != null) {
            items(notifications.sortedByDescending { it.id }) { notification ->
                NotificationItem(notification = notification)
            }
        }
    }
}

@Composable
fun NotificationItem(notification: Notification) {
    Card(
        modifier = Modifier.padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Text(
            text = notification.title,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = notification.description,
            fontSize = 8.sp,
        )
    }
}