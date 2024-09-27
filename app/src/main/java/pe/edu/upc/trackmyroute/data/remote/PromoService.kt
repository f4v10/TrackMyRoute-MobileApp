package pe.edu.upc.trackmyroute.data.remote


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PromoService {
    @GET("ad8173b8-3efd-4cf3-936f-54e901ba6367")
    suspend fun getPromos(): Response<PromosResponseDto>
}