package pe.edu.upc.trackmyroute.feature_login.data.repository

import pe.edu.upc.trackmyroute.feature_login.data.remote.LoginService
import pe.edu.upc.trackmyroute.feature_login.data.remote.UserDto
import pe.edu.upc.trackmyroute.feature_login.data.remote.UserRequest
import pe.edu.upc.trackmyroute.feature_login.domain.repository.LoginRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepositoryImpl (
    private val loginService: LoginService
) : LoginRepository {

    override fun signIn(username: String, password: String, callback: (UserDto) -> Unit) {
        loginService.signIn(UserRequest(username, password)).enqueue(object: Callback<UserDto> {
            override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
                if (response.isSuccessful) {
                    callback(response.body()!!)
                }
            }

            override fun onFailure(call: Call<UserDto>, response: Throwable) {
            }
        })
    }

    override fun signUp(username: String, password: String, callback: (UserDto) -> Unit) {
        loginService.signUp(UserRequest(username, password)).enqueue(object: Callback<UserDto> {
            override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
                if (response.isSuccessful) {
                    callback(response.body()!!)
                }
            }

            override fun onFailure(call: Call<UserDto>, response: Throwable) {
            }
        })
    }
}