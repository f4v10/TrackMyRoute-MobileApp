package pe.edu.upc.trackmyroute.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface RouteService {
    @GET("api/v1/bus-route")
    suspend fun getRoute(): Response<RouteResponseDto>
}