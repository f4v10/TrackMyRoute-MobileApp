package pe.edu.upc.trackmyroute.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.trackmyroute.common.Resource
import pe.edu.upc.trackmyroute.data.local.NotificationDao
import pe.edu.upc.trackmyroute.data.local.NotificationEntity
import pe.edu.upc.trackmyroute.data.remote.NotificationService
import pe.edu.upc.trackmyroute.data.remote.toNotification
import pe.edu.upc.trackmyroute.domain.Notification

class NotificationRepository(
    private val service: NotificationService,
    private val dao: NotificationDao
) {
    suspend fun insertNotification(notification: Notification) = withContext(Dispatchers.IO) {
        dao.insert(
            NotificationEntity(
                notification.id,
                notification.title,
                notification.description
            )
        )
    }

    suspend fun deleteNotification(notification: Notification) = withContext(Dispatchers.IO) {
        dao.delete(
            NotificationEntity(
                notification.id,
                notification.title,
                notification.description
            )
        )
    }

    suspend fun searchNotificationById(id: Int): Resource<List<Notification>> = withContext(Dispatchers.IO) {
        val response = service.searchNotificationById(id)

        if (response.isSuccessful) {
            response.body()?.notifications?.let { notificationsDto ->
                val notifications = mutableListOf<Notification>()
                notificationsDto.forEach { notificationDto ->
                    val notification = notificationDto.toNotification()
                    notifications += notification
                }
                return@withContext Resource.Success(data = notifications.toList())
            }
            return@withContext Resource.Error(message = response.body()?.error ?: "")
        } else {
            return@withContext Resource.Error(message = "An unexpected error occurred")
        }
    }
}