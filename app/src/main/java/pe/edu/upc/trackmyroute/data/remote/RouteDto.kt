package pe.edu.upc.trackmyroute.data.remote

import com.google.gson.annotations.SerializedName
import pe.edu.upc.trackmyroute.domain.Route

data class RouteResponseDto(
    val response: String,
    @SerializedName("results")
    val routes: List<RouteDto>?,
    val error: String?
)

data class RouteDto(
    val busName: String,
    val originName: String,
    val originCoord: String,
    val destinationName: String,
    val destinationCoord: String,
    val totalDistance: String
)

fun RouteDto.toRoute(): Route {
    return Route(busName, originName, originCoord, destinationName, destinationCoord, totalDistance)
}