package pe.edu.upc.trackmyroute.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.trackmyroute.common.Resource
import pe.edu.upc.trackmyroute.data.local.PromoEntity
import pe.edu.upc.trackmyroute.data.remote.PromoService
import pe.edu.upc.trackmyroute.data.remote.toPromo
import pe.edu.upc.trackmyroute.domain.Promo

class PromoRepository(
    private val promoService: PromoService,
    private val promoDao: pe.edu.upc.trackmyroute.data.local.PromoDao
) {

    private suspend fun isFavorite(id: String): Boolean = withContext(Dispatchers.IO) {
        promoDao.fetchPromoById(id)?.let {
            return@withContext true
        }
        return@withContext false
    }

    suspend fun searchPromo(title: String): Resource<List<Promo>> = withContext(Dispatchers.IO) {
        val response = promoService.searchPromo(title)

        if (response.isSuccessful) {
            response.body()?.promos?.let { promosDto ->
                val promos = mutableListOf<Promo>()
                promosDto.forEach { promoDto: pe.edu.upc.trackmyroute.data.remote.PromoDto ->
                    val promo = promoDto.toPromo()
                    promo.isFavorite = isFavorite(promoDto.id)
                    promos += promo
                }
                return@withContext Resource.Success(data = promos.toList())
            }
            return@withContext Resource.Error(message = response.body()?.error ?: "")
        } else {
            return@withContext Resource.Error(message = "Data not found")
        }
    }

    suspend fun insertPromo(id: String, title: String, description: String, imageUrl: String) =
        withContext(Dispatchers.IO) {
            promoDao.insert(PromoEntity(id, title, description, imageUrl))
        }

    suspend fun deletePromo(id: String, title: String, description: String, imageUrl: String) =
        withContext(Dispatchers.IO) {
            promoDao.delete(PromoEntity(id, title, description, imageUrl))
        }
}