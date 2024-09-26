package pe.edu.upc.trackmyroute.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotificationDao {
    @Insert
    suspend fun insert(notificationEntity: NotificationEntity)

    @Delete
    suspend fun delete(notificationEntity: NotificationEntity)

    @Update
    suspend fun update(notificationEntity: NotificationEntity)

    @Query("select * from notifications")
    suspend fun fetchAll(): List<NotificationEntity>

    @Query("select * from notifications where id = :id")
    suspend fun fetchById(id: Int): NotificationEntity?
}