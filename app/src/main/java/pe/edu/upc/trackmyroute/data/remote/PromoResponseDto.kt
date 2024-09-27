package pe.edu.upc.trackmyroute.data.remote

import com.google.gson.annotations.SerializedName
import pe.edu.upc.trackmyroute.domain.Promo

data class PromosResponseDto(
    @SerializedName("promos")
    val promos: List<PromoDto>
)

data class PromoDto(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val isFavorite: Boolean
)

fun PromoDto.toPromo(): Promo {
    return Promo(id, title, description, imageUrl, isFavorite)
}
