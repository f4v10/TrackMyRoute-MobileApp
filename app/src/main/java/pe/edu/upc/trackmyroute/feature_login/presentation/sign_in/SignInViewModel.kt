package pe.edu.upc.trackmyroute.feature_login.presentation.sign_in

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import pe.edu.upc.trackmyroute.common.Resource
import pe.edu.upc.trackmyroute.feature_login.domain.use_case.SignInUseCase

class SignInViewModel(private val signInUseCase: SignInUseCase) : ViewModel(){
    private val _username = mutableStateOf("")
    val username: State<String> = _username

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _state = mutableStateOf(SignInState())
    val state: State<SignInState> = _state

    fun onUsernameChanged(username: String) {
        this._username.value = username
    }

    fun onPasswordChanged(password: String) {
        this._password.value = password
    }

    fun signIn() {
        signInUseCase.invoke(username.value, password.value) { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = SignInState(user = result.data)
                }
                is Resource.Error -> {
                    _state.value = SignInState(error = result.message?:"An error occurred")
                }
                is Resource.Loading -> {
                    _state.value = SignInState(isLoading = true)
                }
            }
        }
    }

}