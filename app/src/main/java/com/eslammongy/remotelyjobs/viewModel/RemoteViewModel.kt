package com.eslammongy.remotelyjobs.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.eslammongy.remotelyjobs.repository.RemoteJobsRepo

class RemoteViewModel(
    application: Application,
    private val remoteJobsRepo: RemoteJobsRepo
) : AndroidViewModel(application) {

    fun remoteSoftwareJobsResult() = remoteJobsRepo.getRemoteJobsResponse("software-dev")

    fun remoteGraphicJobsResult() = remoteJobsRepo.getRemoteJobsResponse("design")

    fun remoteMarketingJobsResult() = remoteJobsRepo.getRemoteJobsResponse("marketing")

    fun remoteCustomerJobsResult() = remoteJobsRepo.getRemoteJobsResponse("customer-support")

}