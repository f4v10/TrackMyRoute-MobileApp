package pe.edu.upc.trackmyroute.presentation.tracking

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pe.edu.upc.trackmyroute.common.Resource
import pe.edu.upc.trackmyroute.data.repository.RouteRepository
import pe.edu.upc.trackmyroute.domain.Route

class RouteViewModel(private val repository: RouteRepository): ViewModel(){
    private val _state = mutableStateOf(RouteState())
    val state: State<RouteState> get() = _state

    fun getRoute(){
        viewModelScope.launch {
            val result = repository.getRoute()
            if (result is Resource.Success) {
                _state.value = RouteState(routes = result.data)
            } else {
                _state.value = RouteState(message = result.message ?: "An error occurred")
            }
        }
    }
}

data class RouteState(
    val routes: List<Route>? = null,
    val message: String = ""
)