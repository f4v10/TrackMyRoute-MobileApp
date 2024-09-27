package pe.edu.upc.trackmyroute.feature_login.domain.repository

import pe.edu.upc.trackmyroute.feature_login.data.remote.UserDto

interface LoginRepository {
    fun signIn(
        username: String,
        password: String,
        callback: (UserDto) -> Unit
    )

    fun signUp(
        username: String,
        password: String,
        callback: (UserDto) -> Unit
    )
}