package pe.edu.upc.trackmyroute.presentation.promo

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pe.edu.upc.trackmyroute.common.Resource
import pe.edu.upc.trackmyroute.data.repository.PromoRepository
import pe.edu.upc.trackmyroute.domain.Promo
import java.io.IOException

class PromoListViewModel(private val promoRepository: PromoRepository) : ViewModel() {

    private val _state = mutableStateOf(PromoListState())
    val state: State<PromoListState> = _state

    init {
        getPromos()
    }

    fun getPromos() {
        _state.value = _state.value.copy(isLoading = true, error = "")
        viewModelScope.launch {
            when (val result = promoRepository.getPromos()) {
                is Resource.Success -> {
                    _state.value = PromoListState(promos = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = PromoListState(error = result.message ?: "An unknown error occurred")
                }
            }
        }
    }

    fun onToggleFavorite(promo: Promo) {
        viewModelScope.launch {
            try {
                val updatedPromo = promoRepository.toggleFavorite(promo)
                val updatedPromos = _state.value.promos.map {
                    if (it.id == updatedPromo.id) updatedPromo else it
                }
                _state.value = _state.value.copy(promos = updatedPromos)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = "Error updating favorite: ${e.localizedMessage}"
                )
            }
        }
    }
}

