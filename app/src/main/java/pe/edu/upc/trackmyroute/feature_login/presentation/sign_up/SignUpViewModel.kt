package pe.edu.upc.trackmyroute.feature_login.presentation.sign_up

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import pe.edu.upc.trackmyroute.common.Resource
import pe.edu.upc.trackmyroute.feature_login.domain.use_case.SignUpUseCase

class SignUpViewModel (private val signUpUseCase: SignUpUseCase) : ViewModel() {
    private val _username = mutableStateOf("")
    val username: State<String> = _username

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _state = mutableStateOf(SignUpState())
    val state: State<SignUpState> = _state

    fun onUsernameChanged(username: String) {
        this._username.value = username
    }

    fun onPasswordChanged(password: String) {
        this._password.value = password
    }

    fun signUp() {
        signUpUseCase.invoke(username.value, password.value) { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = SignUpState(user = result.data)
                }
                is Resource.Error -> {
                    _state.value = SignUpState(error = result.message?:"An error occurred")
                }
                is Resource.Loading -> {
                    _state.value = SignUpState(isLoading = true)
                }
            }
        }
    }
}