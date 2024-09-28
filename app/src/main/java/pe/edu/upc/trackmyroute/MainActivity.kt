package pe.edu.upc.trackmyroute

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import pe.edu.upc.trackmyroute.common.Constants
import pe.edu.upc.trackmyroute.feature_login.data.remote.LoginService
import pe.edu.upc.trackmyroute.feature_login.data.repository.LoginRepositoryImpl
import pe.edu.upc.trackmyroute.feature_login.domain.use_case.SignInUseCase
import pe.edu.upc.trackmyroute.feature_login.domain.use_case.SignUpUseCase
import pe.edu.upc.trackmyroute.feature_login.presentation.sign_in.SignInScreen
import pe.edu.upc.trackmyroute.feature_login.presentation.sign_in.SignInViewModel
import pe.edu.upc.trackmyroute.feature_login.presentation.sign_up.SignUpViewModel
import pe.edu.upc.trackmyroute.ui.theme.TrackMyRouteMobileAppTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    private val loginService = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(LoginService::class.java)

    private val loginRepositorySignUp = LoginRepositoryImpl(loginService)
    private val useCaseSignUp = SignUpUseCase(loginRepositorySignUp)
    private val viewModelSignUp = SignUpViewModel(useCaseSignUp)

    private val loginRepositorySignIn = LoginRepositoryImpl(loginService)
    private val useCaseSignIn = SignInUseCase(loginRepositorySignIn)
    private val viewModelSignIn = SignInViewModel(useCaseSignIn)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrackMyRouteMobileAppTheme {
               //SignUpScreen(viewModelSignUp)
               SignInScreen(viewModelSignIn)
            }
        }
    }
}


