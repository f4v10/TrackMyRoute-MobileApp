package pe.edu.upc.trackmyroute.feature_login.data.remote

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("api/v1/authentication/sign-in")
    fun signIn(@Body request: UserRequest): Call<UserDto>

    @POST("api/v1/authentication/sign-up")
    fun signUp(@Body request: UserRequest): Call<UserDto>
}