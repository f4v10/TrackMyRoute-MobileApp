package pe.edu.upc.trackmyroute.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pe.edu.upc.trackmyroute.common.Resource
import pe.edu.upc.trackmyroute.data.repository.NotificationRepository
import pe.edu.upc.trackmyroute.domain.Notification

class NotificationListViewModel(private val notificationRepository: NotificationRepository): ViewModel() {
    private val _id = mutableIntStateOf(0)
    val id: State<Int> = _id

    private val _state = mutableStateOf(NotificationListState())
    val state: State<NotificationListState> = _state

    private val _title = mutableStateOf("")
    val title: State<String> = _title

    init {
        searchNotificationById()
    }

    fun onTitleChange(title: String) {
        _title.value = title
    }

    fun searchNotificationById() {
        _state.value = _state.value.copy(isLoading = true, error = "")
        viewModelScope.launch {
            try {
                val result = notificationRepository.searchNotificationById(_id.value)
                if (result is Resource.Success) {
                    _state.value = NotificationListState(notifications = result.data)
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = e.message ?: "An unexpected error occurred")
            }
        }
    }
}