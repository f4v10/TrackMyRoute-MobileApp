package pe.edu.upc.trackmyroute.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PromoDao {
    @Insert
    suspend fun insert(promoEntity: PromoEntity)

    @Insert
    suspend fun delete(promoEntity: PromoEntity)

    @Query("select * from promos where id = :id")
    suspend fun fetchPromoById(id: String): PromoEntity?
}