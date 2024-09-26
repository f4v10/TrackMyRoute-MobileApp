package pe.edu.upc.trackmyroute

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.room.Room
import pe.edu.upc.trackmyroute.common.Constants
import pe.edu.upc.trackmyroute.core.AppDatabase
import pe.edu.upc.trackmyroute.data.remote.PromoService
import pe.edu.upc.trackmyroute.data.repository.PromoRepository
import pe.edu.upc.trackmyroute.presentation.promo.PromoListScreen
import pe.edu.upc.trackmyroute.presentation.promo.PromoListViewModel
import pe.edu.upc.trackmyroute.ui.theme.TrackMyRouteMobileAppTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val service = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PromoService::class.java)

        val dao = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "db-promos"
        ).build().getPromoDao()

        val viewModel = PromoListViewModel(PromoRepository(service, dao))

        enableEdgeToEdge()
        setContent {
            TrackMyRouteMobileAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PromoListScreen(viewModel)
                }
            }
        }
    }
}