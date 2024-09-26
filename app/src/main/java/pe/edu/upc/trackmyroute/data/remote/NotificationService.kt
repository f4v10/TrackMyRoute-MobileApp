package pe.edu.upc.trackmyroute.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NotificationService {
    @GET("api/v1/notifications/{id}")
    suspend fun searchNotificationById(@Path("id") id: Int): Response<NotificationResponseDto>
}