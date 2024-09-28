package pe.edu.upc.trackmyroute.feature_login.data.remote

import com.google.gson.annotations.SerializedName

data class UserRequest(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String
)