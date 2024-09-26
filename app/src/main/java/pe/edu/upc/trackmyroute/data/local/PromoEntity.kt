package pe.edu.upc.trackmyroute.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "promos")
data class PromoEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("description")
    val description: String,
    @ColumnInfo("imageUrl")
    val imageUrl: String
)