package net.cpeek.semesterproject.db

import androidx.room.Database
import androidx.room.RoomDatabase
import net.cpeek.semesterproject.data.*

@Database(entities = [Player::class, Team::class, Line::class], version = 7)
abstract class HockeyDB : RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun teamDao(): TeamDao
    abstract fun lineDao(): LineDao
}