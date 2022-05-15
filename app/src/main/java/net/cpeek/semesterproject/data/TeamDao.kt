package net.cpeek.semesterproject.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TeamDao {
    @Insert
    fun insert(team: Team)

    @Delete
    fun delete(team: Team)

    @Query("SELECT * FROM players WHERE team_id = :teamID")
    fun getAllPlayersOnTeam(teamID: Int): Array<Player>

    @Query("SELECT * FROM teams WHERE id = :teamID")
    fun getTeamByID(teamID: Int): Team

    @Query("SELECT * FROM teams")
    fun getAllTeams(): Array<Team>
}