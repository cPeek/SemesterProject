package net.cpeek.semesterproject.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

// Class for storing player data.
// All height/weight measurements are in metric units (m, kg)
//
// Handedness
//  - Righty    = true
//  - Lefty     = false

@Entity(tableName = "players")
data class Player(
    @ColumnInfo(name = "first_name")    var firstName:  String,
    @ColumnInfo(name = "last_name")     var lastName:   String,
    @ColumnInfo(name = "number")        var number:     Int,
    @ColumnInfo(name = "team_id")       val team_id:    Int,
    @ColumnInfo(name = "position")      var position:   String,
    @ColumnInfo(name = "hand")          var hand:       Boolean,
    @ColumnInfo(name = "height")        var height:     Int?,
    @ColumnInfo(name = "weight")        var weight:     Int?,
    @ColumnInfo(name = "birthday")      var birthday:   Int?,
    @PrimaryKey(autoGenerate = true)    val id:         Int = 0,
){
    override fun toString(): String {
        return "$firstName $lastName"
    }
}