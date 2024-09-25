package pe.edu.upc.trackmyroute

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import pe.edu.upc.trackmyroute.common.Constants
import pe.edu.upc.trackmyroute.core.AppDatabase
import pe.edu.upc.trackmyroute.data.remote.RouteService
import pe.edu.upc.trackmyroute.data.repository.RouteRepository
import pe.edu.upc.trackmyroute.presentation.tracking.RouteScreen
import pe.edu.upc.trackmyroute.presentation.tracking.RouteViewModel
import pe.edu.upc.trackmyroute.ui.theme.TrackMyRouteMobileAppTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val service = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RouteService::class.java)

        val dao = Room
            .databaseBuilder(applicationContext, AppDatabase::class.java, "jokes-db")
            .build()
            .getRouteDao()

        val repository = RouteRepository(service, dao)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrackMyRouteMobileAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val routeViewModel = RouteViewModel(repository)
                    RouteScreen(routeViewModel)
                }
            }
        }
    }
}

