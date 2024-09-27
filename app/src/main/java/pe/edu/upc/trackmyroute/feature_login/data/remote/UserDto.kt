package pe.edu.upc.trackmyroute.feature_login.data.remote

import com.google.gson.annotations.SerializedName
import pe.edu.upc.trackmyroute.feature_login.domain.model.User

data class UserDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String
)

fun UserDto.toUser(): User {
    return User(username = username)
}