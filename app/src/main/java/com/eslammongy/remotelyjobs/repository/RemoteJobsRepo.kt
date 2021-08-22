package com.eslammongy.remotelyjobs.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eslammongy.remotelyjobs.api.RetrofitBuilder
import com.eslammongy.remotelyjobs.model.RemoteJobs
import com.eslammongy.remotelyjobs.other.Constants.TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteJobsRepo {

    private val remoteJobsServices = RetrofitBuilder.API_SERVICES
    private var remoteJobsLiveData:MutableLiveData<RemoteJobs> = MutableLiveData()

    init {
        getRemoteSoftWareJobsResponse()
        getRemoteGraphicJobsResponse()
    }

    private fun getRemoteSoftWareJobsResponse(){

        remoteJobsServices.getRemoteJobsResponse("software-dev").enqueue(
            object : Callback<RemoteJobs>{
                override fun onResponse(call: Call<RemoteJobs>, response: Response<RemoteJobs>) {
                    remoteJobsLiveData.postValue(response.body())
                }

                override fun onFailure(call: Call<RemoteJobs>, t: Throwable) {
                    remoteJobsLiveData.postValue(null)
                    Log.e(TAG , "onFailure ..${t.message}")
                }
            })
    }

    fun remoteSoftWareJobsResult():LiveData<RemoteJobs>{
        return remoteJobsLiveData
    }

    private fun getRemoteGraphicJobsResponse(){
        remoteJobsLiveData.value = null
        remoteJobsServices.getRemoteJobsResponse("design").enqueue(
            object : Callback<RemoteJobs>{
                override fun onResponse(call: Call<RemoteJobs>, response: Response<RemoteJobs>) {
                    remoteJobsLiveData.postValue(response.body())
                }

                override fun onFailure(call: Call<RemoteJobs>, t: Throwable) {
                    remoteJobsLiveData.postValue(null)
                    Log.e(TAG , "onFailure ..${t.message}")
                }
            })
    }

    fun remoteGraphicJobsResult():LiveData<RemoteJobs>{
        return remoteJobsLiveData
    }

}