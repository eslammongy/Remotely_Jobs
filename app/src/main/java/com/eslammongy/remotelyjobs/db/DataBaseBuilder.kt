package com.eslammongy.remotelyjobs.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.eslammongy.remotelyjobs.model.JobEntity


@Database(entities = [JobEntity::class] , version = 1)
abstract class DataBaseBuilder : RoomDatabase() {

    abstract  fun getSavedJobsDao():SavedJobsDao

    companion object{
        @Volatile
        private var roomInstance:DataBaseBuilder? = null
        private val Lock = Any()

        operator fun invoke(context: Context) = roomInstance ?: synchronized(Lock){
            roomInstance ?: createDataBase(context).also{ roomInstance = it}
        }

        private fun createDataBase(context:Context) = Room.databaseBuilder(
            context.applicationContext,
            DataBaseBuilder::class.java,
            "SavedJobs.db"
        ).build()
    }
}