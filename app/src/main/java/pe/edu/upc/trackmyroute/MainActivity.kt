package pe.edu.upc.trackmyroute

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import pe.edu.upc.trackmyroute.common.Constants
import pe.edu.upc.trackmyroute.feature_login.data.remote.LoginService
import pe.edu.upc.trackmyroute.feature_login.data.repository.LoginRepositoryImpl
import pe.edu.upc.trackmyroute.feature_login.domain.model.User
import pe.edu.upc.trackmyroute.feature_login.domain.use_case.SignInUseCase
import pe.edu.upc.trackmyroute.feature_login.domain.use_case.SignUpUseCase
import pe.edu.upc.trackmyroute.feature_login.presentation.sign_in.SignInScreen
import pe.edu.upc.trackmyroute.feature_login.presentation.sign_in.SignInState
import pe.edu.upc.trackmyroute.feature_login.presentation.sign_in.SignInViewModel
import pe.edu.upc.trackmyroute.feature_login.presentation.sign_up.SignUpScreen
import pe.edu.upc.trackmyroute.feature_login.presentation.sign_up.SignUpState
import pe.edu.upc.trackmyroute.feature_login.presentation.sign_up.SignUpViewModel
import pe.edu.upc.trackmyroute.ui.theme.TrackMyRouteMobileAppTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Modifier

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


