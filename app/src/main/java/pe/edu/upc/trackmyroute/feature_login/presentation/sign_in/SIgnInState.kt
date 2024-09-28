package pe.edu.upc.trackmyroute.feature_login.presentation.sign_in

import pe.edu.upc.trackmyroute.feature_login.domain.model.User

data class SignInState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String = ""
)
