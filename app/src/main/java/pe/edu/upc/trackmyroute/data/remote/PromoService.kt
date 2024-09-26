package pe.edu.upc.trackmyroute.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PromoService {

    @GET("search/{title}")
    suspend fun searchPromo(@Path("title") title: String): Response<PromosResponseDto>
}