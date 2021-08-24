package com.eslammongy.remotelyjobs.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.eslammongy.remotelyjobs.model.JobEntity

@Dao
interface SavedJobsDao {

    @Query("SELECT * FROM Saved_Jobs ORDER BY id DESC")
     fun getAllSavedJobs(): LiveData<List<JobEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNewJobs(jobEntity: JobEntity):Long

    @Delete
    suspend fun deleteSelectedJob(jobEntity: JobEntity)

}