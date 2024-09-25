package pe.edu.upc.trackmyroute.domain

import androidx.room.ColumnInfo

data class Route(
    val busName: String,
    val originName: String,
    val originCoord: String,
    val destinationName: String,
    val destinationCoord: String,
    val totalDistance: String
)
