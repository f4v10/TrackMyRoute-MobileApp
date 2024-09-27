package pe.edu.upc.trackmyroute.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.trackmyroute.common.Resource
import pe.edu.upc.trackmyroute.data.local.PromoDao
import pe.edu.upc.trackmyroute.data.local.PromoEntity
import pe.edu.upc.trackmyroute.data.remote.PromoService
import pe.edu.upc.trackmyroute.data.remote.toPromo
import pe.edu.upc.trackmyroute.domain.Promo


class PromoRepository(
    private val promoService: PromoService,
    private val promoDao: PromoDao
) {
    suspend fun getPromos(): Resource<List<Promo>> = withContext(Dispatchers.IO) {
        try {
            val response = promoService.getPromos()
            if (response.isSuccessful) {
                response.body()?.promos?.let { promosDto ->
                    val promos = promosDto.map { it.toPromo() }
                    return@withContext Resource.Success(data = promos)
                }
                return@withContext Resource.Error(message = "No promos found")
            } else {
                return@withContext Resource.Error(message = "Error fetching promos: ${response.code()}")
            }
        } catch (e: Exception) {
            return@withContext Resource.Error(message = e.localizedMessage ?: "An error occurred")
        }
    }

    suspend fun toggleFavorite(promo: Promo) = withContext(Dispatchers.IO) {
        val updatedPromo = promo.copy(isFavorite = !promo.isFavorite)
        if (updatedPromo.isFavorite) {
            promoDao.insert(PromoEntity(updatedPromo.id, updatedPromo.title, updatedPromo.description, updatedPromo.imageUrl))
        } else {
            promoDao.delete(PromoEntity(updatedPromo.id, updatedPromo.title, updatedPromo.description, updatedPromo.imageUrl))
        }
        updatedPromo
    }
}