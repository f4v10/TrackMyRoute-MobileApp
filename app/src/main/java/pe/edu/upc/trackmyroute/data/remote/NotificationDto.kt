package pe.edu.upc.trackmyroute.data.remote

import pe.edu.upc.trackmyroute.domain.Notification

data class NotificationResponseDto(
    val notifications: List<NotificationDto>?,
    val error: String?
)

data class NotificationDto(
    val id: Int,
    val title: String,
    val description: String
)

fun NotificationDto.toNotification(): Notification {
    return Notification(
        id,
        title,
        description
    )
}
