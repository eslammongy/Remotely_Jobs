package com.eslammongy.remotelyjobs.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eslammongy.remotelyjobs.api.RetrofitBuilder
import com.eslammongy.remotelyjobs.db.DataBaseBuilder
import com.eslammongy.remotelyjobs.model.JobEntity
import com.eslammongy.remotelyjobs.model.RemoteJobs
import com.eslammongy.remotelyjobs.other.Constants.TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteJobsRepo (private val jobsDB:DataBaseBuilder){

    private val remoteJobsServices = RetrofitBuilder.API_SERVICES
    private var remoteJobsLiveData:MutableLiveData<RemoteJobs> = MutableLiveData()
    private var searchListJobsLiveData:MutableLiveData<RemoteJobs> = MutableLiveData()

     fun getRemoteJobsResponse(category:String):LiveData<RemoteJobs>{
         remoteJobsLiveData.value = null
        remoteJobsServices.getRemoteJobsResponse(category).enqueue(
            object : Callback<RemoteJobs>{
                override fun onResponse(call: Call<RemoteJobs>, response: Response<RemoteJobs>) {
                    remoteJobsLiveData.postValue(response.body())
                }

                override fun onFailure(call: Call<RemoteJobs>, t: Throwable) {
                    remoteJobsLiveData.postValue(null)
                    Log.e(TAG , "onFailure ..${t.message}")
                }
            })
        return remoteJobsLiveData
    }

    fun searchForRemoteJobs(query:String){
        remoteJobsServices.searchRemoteJobsResponse(query).enqueue(object :Callback<RemoteJobs>{
            override fun onResponse(call: Call<RemoteJobs>, response: Response<RemoteJobs>) {
                searchListJobsLiveData.postValue(response.body())
            }
            override fun onFailure(call: Call<RemoteJobs>, t: Throwable) {
                searchListJobsLiveData.postValue(null)
                Log.e(TAG , "onFailure ..${t.message}")
            }
        })

    }

    fun  getSearchResult():LiveData<RemoteJobs> {
        return searchListJobsLiveData
    }

    suspend fun addNewFavJobs(jobEntity: JobEntity) = jobsDB.getSavedJobsDao().saveNewJobs(jobEntity)
    fun getAllFavJobs() = jobsDB.getSavedJobsDao().getAllSavedJobs()
    suspend fun deleteFavJobs(jobEntity: JobEntity) = jobsDB.getSavedJobsDao().deleteSelectedJob(jobEntity)

}