package pe.edu.upc.trackmyroute.domain

data class Promo(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    var isFavorite: Boolean
)