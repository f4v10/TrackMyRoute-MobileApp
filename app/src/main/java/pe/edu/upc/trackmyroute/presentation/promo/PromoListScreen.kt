package pe.edu.upc.trackmyroute.presentation.promo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.trackmyroute.domain.Promo
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromoListScreen(viewModel: PromoListViewModel) {
    val state = viewModel.state.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Promociones Disponibles") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }
                state.error.isNotEmpty() -> {
                    Text(
                        text = state.error,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                    Button(
                        onClick = { viewModel.getPromos() },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text("Retry")
                    }
                }
                state.promos.isEmpty() -> {
                    Text(
                        "No se encontraron promociones",
                        modifier = Modifier.padding(16.dp)
                    )
                }
                else -> {
                    PromoList(
                        promos = state.promos,
                        onToggleFavorite = viewModel::onToggleFavorite
                    )
                }
            }
        }
    }
}

@Composable
fun PromoList(
    promos: List<Promo>,
    onToggleFavorite: (Promo) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(promos) { promo ->
            PromoItem(promo = promo, onToggleFavorite = { onToggleFavorite(promo) })
        }
    }
}

@Composable
fun PromoItem(promo: Promo, onToggleFavorite: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            AsyncImage(
                model = promo.imageUrl,
                contentDescription = promo.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = promo.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = onToggleFavorite) {
                        Icon(
                            imageVector = if (promo.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = if (promo.isFavorite) "Remove from favorites" else "Add to favorites",
                            tint = if (promo.isFavorite) Color.Red else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = promo.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}