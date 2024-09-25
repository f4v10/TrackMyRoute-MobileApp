package pe.edu.upc.trackmyroute.core

import androidx.room.Database
import androidx.room.RoomDatabase
import pe.edu.upc.trackmyroute.data.local.RouteDao
import pe.edu.upc.trackmyroute.data.local.RouteEntity


@Database(entities = [RouteEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getRouteDao(): RouteDao
}