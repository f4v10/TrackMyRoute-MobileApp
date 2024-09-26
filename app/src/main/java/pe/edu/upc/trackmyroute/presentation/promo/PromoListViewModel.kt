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

    private val _title = mutableStateOf("")
    val title: State<String> = _title

    private val _state = mutableStateOf(PromoListState())
    val state: State<PromoListState> = _state

    init {
        searchPromo()
    }

    fun onTitleChanged(title: String) {
        _title.value = title
    }

    fun onToggleFavorite(promo: Promo) {
        viewModelScope.launch {
            try {
                val updatedPromo = promo.copy(isFavorite = !promo.isFavorite)
                if (updatedPromo.isFavorite) {
                    promoRepository.insertPromo(updatedPromo.id, updatedPromo.title, updatedPromo.description, updatedPromo.imageUrl)
                } else {
                    promoRepository.deletePromo(updatedPromo.id, updatedPromo.title, updatedPromo.description, updatedPromo.imageUrl)
                }
                updatePromoList(updatedPromo)
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = "Error updating favorite: ${e.localizedMessage}")
            }
        }
    }

    fun searchPromo() {
        _state.value = _state.value.copy(isLoading = true, error = "")
        viewModelScope.launch {
            try {
                val result = promoRepository.searchPromo(_title.value)
                _state.value = when (result) {
                    is Resource.Success -> {
                        PromoListState(promos = result.data?.sortedByDescending { it.isFavorite } ?: emptyList())
                    }
                    is Resource.Error -> {
                        PromoListState(error = result.message ?: "An unknown error occurred")
                    }
                }
            } catch (e: IOException) {
                _state.value = PromoListState(error = "Network error: Unable to connect to the server. Please check your internet connection.")
            } catch (e: Exception) {
                _state.value = PromoListState(error = "An unexpected error occurred: ${e.localizedMessage}")
            }
        }
    }

    private fun updatePromoList(updatedPromo: Promo) {
        val updatedPromos = _state.value.promos.map { promo ->
            if (promo.id == updatedPromo.id) updatedPromo else promo
        }.sortedByDescending { it.isFavorite }

        _state.value = _state.value.copy(promos = updatedPromos)
    }
}