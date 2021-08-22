package com.eslammongy.remotelyjobs.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class JobModel(
    @SerializedName("candidate_required_location")
    val candidateRequiredLocation: String?,
    val category: String?,
    @SerializedName("company_logo_url")
    val companyLogoUrl: String?,
    @SerializedName("company_name")
    val companyName: String?,
    val description: String?,
    val id: Int?,
    @SerializedName("job_type")
    val jobType: String?,
    @SerializedName("publication_date")
    val publicationDate: String?,
    val salary: String?,
    val tags: List<String>?,
    val title: String?,
    val url: String?
): Parcelable
