package com.eslammongy.remotelyjobs.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eslammongy.remotelyjobs.HomeActivity
import com.eslammongy.remotelyjobs.databinding.JobItemListBinding
import com.eslammongy.remotelyjobs.fragments.CustomWebView
import com.eslammongy.remotelyjobs.model.JobModel

class RemoteJobsAdapter (private val context: Context):RecyclerView.Adapter<RemoteJobsAdapter.RemoteJobsViewHolder>(){

    private var binding:JobItemListBinding? = null

    private val diffUtilCallback = object :DiffUtil.ItemCallback<JobModel>(){
        override fun areItemsTheSame(oldItem: JobModel, newItem: JobModel): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: JobModel, newItem: JobModel): Boolean {
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

          //  binding?.tvJobLocation!!.text = currentJob.candidateRequiredLocation
            binding?.tvJobTitle!!.text = currentJob.title
            binding?.tvJobType!!.text = currentJob.jobType
            binding?.tvCompanyName!!.text = currentJob.companyName

            val dateJob = currentJob.publicationDate?.split("T")
            binding?.tvDate!!.text = dateJob?.get(0)
        }.setOnClickListener {

                CustomWebView(currentJob).show((context as HomeActivity).supportFragmentManager , "TAG")

//             val navDirection = SoftEngineerFragmentDirections.actionSoftEngineerFragmentToJobDetailesFragment(currentJob)
//            it.findNavController().navigate(navDirection)
        }
    }

    override fun getItemCount(): Int {
        return  differ.currentList.size
    }

    inner  class RemoteJobsViewHolder(itemBinding:JobItemListBinding):RecyclerView.ViewHolder(itemBinding.root)

}