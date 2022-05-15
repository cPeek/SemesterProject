package net.cpeek.semesterproject.data

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

// Data Class to store team data.
@Entity(tableName = "teams")
data class Team(
    @PrimaryKey(autoGenerate = true)    var id: Int = 0,
    @ColumnInfo(name = "team_name")     var teamName: String,
    @ColumnInfo(name = "img")           var img: ByteArray?
){
    override fun toString(): String {
        return teamName
    }
}