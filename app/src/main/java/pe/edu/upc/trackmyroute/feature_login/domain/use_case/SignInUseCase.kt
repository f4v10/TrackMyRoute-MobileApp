package pe.edu.upc.trackmyroute.feature_login.domain.use_case

import pe.edu.upc.trackmyroute.common.Resource
import pe.edu.upc.trackmyroute.feature_login.data.remote.toUser
import pe.edu.upc.trackmyroute.feature_login.domain.model.User
import pe.edu.upc.trackmyroute.feature_login.domain.repository.LoginRepository

class SignInUseCase(private val loginRepository: LoginRepository) {
    operator fun invoke(username: String, password: String, callback: (Resource<User>) -> Unit) {
        callback(Resource.Loading())
        loginRepository.signIn(username, password) { result ->
            callback(Resource.Success(data = result.toUser()))
        }
    }
}