package pe.edu.upc.trackmyroute.presentation.promo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromoListScreen(viewModel: PromoListViewModel) {
    val title = viewModel.title.value
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
            SearchBar(
                title = title,
                onTitleChanged = viewModel::onTitleChanged,
                onSearch = viewModel::searchPromo
            )

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
                        onClick = { viewModel.searchPromo() },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text("Retry")
                    }
                }
                state.promos.isEmpty() -> {
                    Text(
                        "No promotions found",
                        modifier = Modifier.padding(16.dp)
                    )
                }
                else -> {
                    PromoList(
                        promos = state.promos ?: emptyList(),
                        onToggleFavorite = viewModel::onToggleFavorite
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    title: String,
    onTitleChanged: (String) -> Unit,
    onSearch: () -> Unit
) {
    OutlinedTextField(
        value = title,
        onValueChange = onTitleChanged,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = { Text("Buscar promociones") },
        trailingIcon = {
            IconButton(onClick = onSearch) {
                Icon(Icons.Default.Search, contentDescription = "Buscar")
            }
        },
        singleLine = true
    )
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
        items(promos.sortedByDescending { it.isFavorite }) { promo ->
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
            GlideImage(
                imageModel = { promo.imageUrl },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                loading = {
                    Box(modifier = Modifier.matchParentSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                },
                failure = {
                    Text(
                        text = "Image not available",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = promo.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = onToggleFavorite) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorito",
                            tint = if (promo.isFavorite) Color.Red else Color.Gray
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = promo.description,
                    fontSize = 14.sp
                )
            }
        }
    }
}