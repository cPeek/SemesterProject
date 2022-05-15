package net.cpeek.semesterproject.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlayerDao {
    @Insert
    fun insert(player: Player)

    @Delete
    fun delete(player: Player)

    @Query("SELECT * FROM players WHERE id = :playerID")
    fun getPlayerByID(playerID: Int): Player
}