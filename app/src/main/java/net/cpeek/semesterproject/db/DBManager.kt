package net.cpeek.semesterproject.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

object DBManager {
    var db: HockeyDB? = null

    fun getDB(context: Context): HockeyDB{
        if(db == null){
            db = Room.databaseBuilder(context, HockeyDB::class.java, "hockeyDB2")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
        return db!!
    }
}