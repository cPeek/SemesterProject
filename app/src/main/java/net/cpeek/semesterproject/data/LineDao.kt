package net.cpeek.semesterproject.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LineDao {
    @Insert
    fun insert(line: Line)

    @Delete
    fun delete(line: Line)

    @Query("SELECT * FROM lines WHERE id=:lineID")
    fun getLine(lineID: Int): Line

    @Query("SELECT * FROM lines WHERE team=:teamID")
    fun getLinesOfTeam(teamID: Int): Array<Line>
}