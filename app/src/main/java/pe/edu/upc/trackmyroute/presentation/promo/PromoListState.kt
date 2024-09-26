package pe.edu.upc.trackmyroute.presentation.promo

import pe.edu.upc.trackmyroute.domain.Promo

data class PromoListState(
    val isLoading: Boolean = true,
    val promos: List<Promo> = emptyList(),
    val error: String = ""
)