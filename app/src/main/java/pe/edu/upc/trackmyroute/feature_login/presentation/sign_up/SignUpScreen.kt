package pe.edu.upc.trackmyroute.feature_login.presentation.sign_up

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(viewModel: SignUpViewModel) {
    val username = viewModel.username.value
    val password = viewModel.password.value

    val state = viewModel.state.value

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Track My Route") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ){ paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Crear Cuenta", fontSize = 30.sp)

            OutlinedTextField(value = username,
                label = { Text("Usuario") },
                onValueChange = {
                    viewModel.onUsernameChanged(it)
                })
            OutlinedTextField(value = password,
                label = { Text("Contraseña") },
                onValueChange = {
                    viewModel.onPasswordChanged(it)
                })
            OutlinedButton(onClick = {
                viewModel.signUp()
            }) {
                Text(text = "Sign up")
            }
            if (state.isLoading) {
                CircularProgressIndicator()
            }
            state.user?.let {
                Text("Welcome ${viewModel.username.value}")
            }
            if (state.error.isNotBlank()) {
                Text(text = state.error)
            }
        }
    }
}