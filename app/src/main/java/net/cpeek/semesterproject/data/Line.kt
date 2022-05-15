package net.cpeek.semesterproject.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.function.IntToLongFunction

@Entity(tableName = "lines")
data class Line(
    @ColumnInfo(name = "name")          val lineName: String,
    @ColumnInfo(name = "team")          val team_id: Int,
    @ColumnInfo(name = "ld")            val ld: Int,
    @ColumnInfo(name = "rd")            val rd: Int,
    @ColumnInfo(name = "c")             val c: Int,
    @ColumnInfo(name = "lw")            val lw: Int,
    @ColumnInfo(name = "rw")            val rw: Int,
    @PrimaryKey(autoGenerate = true)    val id: Int = 0
) {
    override fun toString(): String {
        return lineName
    }
}