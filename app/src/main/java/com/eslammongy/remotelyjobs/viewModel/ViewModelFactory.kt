package com.eslammongy.remotelyjobs.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eslammongy.remotelyjobs.repository.RemoteJobsRepo

class ViewModelFactory (
    private val application:Application,
    private val remoteJobsRepo: RemoteJobsRepo):ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RemoteViewModel(application , remoteJobsRepo) as T
    }
}