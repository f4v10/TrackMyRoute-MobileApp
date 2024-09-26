package pe.edu.upc.trackmyroute.presentation

import pe.edu.upc.trackmyroute.domain.Notification

data class NotificationListState(
    val isLoading: Boolean = false,
    val notifications: List<Notification>? = null,
    val error: String = ""
)
