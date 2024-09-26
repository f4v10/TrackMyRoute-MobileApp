package pe.edu.upc.trackmyroute.data.remote

import com.google.gson.annotations.SerializedName
import pe.edu.upc.trackmyroute.domain.Promo

data class PromosResponseDto(
    val response: String,
    @SerializedName("results")
    val promos: List<PromoDto>?,
    val error: String?
)

data class PromoDto(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String
)

fun PromoDto.toPromo(): Promo {
    return Promo(id, title, description, imageUrl)
}