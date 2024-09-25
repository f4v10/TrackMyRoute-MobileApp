package pe.edu.upc.trackmyroute.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routes")
data class RouteEntity (
    @PrimaryKey
    val busName: String,
    @ColumnInfo("origin_name")
    val originName: String,
    @ColumnInfo("origin_coord")
    val originCoord: String,
    @ColumnInfo("destination_name")
    val destinationName: String,
    @ColumnInfo("destination_coord")
    val destinationCoord: String,
    @ColumnInfo("total_distance")
    val totalDistance: String
    )