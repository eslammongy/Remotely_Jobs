package com.eslammongy.remotelyjobs.api

import com.eslammongy.remotelyjobs.model.RemoteJobs
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteJobsApi {

    @GET("remote-jobs?limit=6")
    fun getRemoteJobsResponse(@Query("category") query: String?): Call<RemoteJobs>
}