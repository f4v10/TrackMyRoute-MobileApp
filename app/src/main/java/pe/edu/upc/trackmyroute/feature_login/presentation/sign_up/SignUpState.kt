package pe.edu.upc.trackmyroute.feature_login.presentation.sign_up

import pe.edu.upc.trackmyroute.feature_login.domain.model.User

data class SignUpState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String = ""
)
