package com.eslammongy.remotelyjobs.model

import com.google.gson.annotations.SerializedName

data class RemoteJobs(
    @SerializedName("job-count")
    val jobCount: Int?,
    val jobs: List<JobModel>?,
    val legalNotice: String?
)
