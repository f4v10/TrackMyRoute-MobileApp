package pe.edu.upc.trackmyroute.core

import androidx.room.Database
import androidx.room.RoomDatabase
import pe.edu.upc.trackmyroute.data.local.PromoDao
import pe.edu.upc.trackmyroute.data.local.PromoEntity

@Database(entities = [PromoEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getPromoDao(): PromoDao
}