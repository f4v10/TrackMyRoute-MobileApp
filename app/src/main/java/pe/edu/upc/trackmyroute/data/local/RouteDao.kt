package pe.edu.upc.trackmyroute.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RouteDao {

    @Insert
    suspend fun insert(routeEntity: RouteEntity)

    @Delete
    suspend fun delete(routeEntity: RouteEntity)

    @Update
    suspend fun update(routeEntity: RouteEntity)

    @Query("select * from routes")
    suspend fun fetchAll(): List<RouteEntity>

    @Query("select * from routes where busName = :busName")
    suspend fun fetchByBusName(busName: String): RouteEntity?
}