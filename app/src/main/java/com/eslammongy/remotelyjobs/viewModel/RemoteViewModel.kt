package com.eslammongy.remotelyjobs.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.eslammongy.remotelyjobs.model.JobEntity
import com.eslammongy.remotelyjobs.repository.RemoteJobsRepo
import kotlinx.coroutines.launch

class RemoteViewModel(
    application: Application,
    private val remoteJobsRepo: RemoteJobsRepo
) : AndroidViewModel(application) {

    fun remoteSoftwareJobsResult() = remoteJobsRepo.getRemoteJobsResponse("software-dev")

    fun remoteGraphicJobsResult() = remoteJobsRepo.getRemoteJobsResponse("design")

    fun remoteMarketingJobsResult() = remoteJobsRepo.getRemoteJobsResponse("marketing")

    fun remoteCustomerJobsResult() = remoteJobsRepo.getRemoteJobsResponse("customer-support")

    fun insertNewJob(jobEntity: JobEntity) = viewModelScope.launch {
        remoteJobsRepo.addNewFavJobs(jobEntity)
    }
    fun deleteFavJob(jobEntity: JobEntity) = viewModelScope.launch {
        remoteJobsRepo.deleteFavJobs(jobEntity)
    }

    fun getAllSavedJobs() = remoteJobsRepo.getAllFavJobs()


}