package pe.edu.upc.trackmyroute.core

import androidx.room.Database
import androidx.room.RoomDatabase
import pe.edu.upc.trackmyroute.data.local.NotificationDao
import pe.edu.upc.trackmyroute.data.local.NotificationEntity

@Database(entities = [NotificationEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getNotificationDao(): NotificationDao
}