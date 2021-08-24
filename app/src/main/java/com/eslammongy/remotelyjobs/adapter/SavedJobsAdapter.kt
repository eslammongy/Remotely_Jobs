package com.eslammongy.remotelyjobs.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eslammongy.remotelyjobs.HomeActivity
import com.eslammongy.remotelyjobs.databinding.JobItemListBinding
import com.eslammongy.remotelyjobs.fragments.CustomWebView
import com.eslammongy.remotelyjobs.model.JobEntity
import com.eslammongy.remotelyjobs.model.JobModel



class SavedJobsAdapter  (private val context: Context , private val onItemClicked:OnItemClickListener) : RecyclerView.Adapter<SavedJobsAdapter.RemoteJobsViewHolder>(){

    private var binding: JobItemListBinding? = null

    private val diffUtilCallback = object : DiffUtil.ItemCallback<JobEntity>(){
        override fun areItemsTheSame(oldItem: JobEntity, newItem: JobEntity): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: JobEntity, newItem: JobEntity): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtilCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemoteJobsViewHolder {
        binding = JobItemListBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return RemoteJobsViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: RemoteJobsViewHolder, position: Int) {
        val currentJob = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(currentJob.companyLogoUrl).into(binding?.ivCompanyLogo!!)
            binding?.tvJobTitle!!.text = currentJob.title
            binding?.tvJobType!!.text = currentJob.jobType
            binding?.tvCompanyName!!.text = currentJob.companyName
            val dateJob = currentJob.publicationDate?.split("T")
            binding?.tvDate!!.text = dateJob?.get(0)

            binding?.ibDelete!!.visibility = View.VISIBLE
        }.setOnClickListener {
            val tags = arrayListOf<String>()
            val favJob = JobModel(
                currentJob.candidateRequiredLocation, currentJob.category, currentJob.companyLogoUrl,
                currentJob.companyName, currentJob.description, currentJob.jobId, currentJob.jobType,
                currentJob.publicationDate, currentJob.salary,tags ,currentJob.title, currentJob.url)
            CustomWebView(favJob).show((context as HomeActivity).supportFragmentManager , "TAG")
        }
        holder.itemView.apply {
            binding?.ibDelete!!.setOnClickListener {
                onItemClicked.onItemClick(currentJob , binding?.ibDelete!! , position)
            }
        }
    }

    override fun getItemCount(): Int {
        return  differ.currentList.size
    }

    inner  class RemoteJobsViewHolder(itemBinding: JobItemListBinding): RecyclerView.ViewHolder(itemBinding.root)

    interface OnItemClickListener{
        fun onItemClick(jobEntity: JobEntity , view:View , position:Int)
    }

}