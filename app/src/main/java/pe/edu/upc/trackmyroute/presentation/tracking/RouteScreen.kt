package pe.edu.upc.trackmyroute.presentation.tracking

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RouteScreen(viewModel: RouteViewModel){
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier.fillMaxWidth().padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedButton(onClick = {

            }) {
                Text("Ver Rutas")
            }
        }
    }
}