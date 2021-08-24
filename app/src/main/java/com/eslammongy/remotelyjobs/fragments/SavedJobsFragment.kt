package com.eslammongy.remotelyjobs.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eslammongy.remotelyjobs.HomeActivity
import com.eslammongy.remotelyjobs.R
import com.eslammongy.remotelyjobs.adapter.SavedJobsAdapter
import com.eslammongy.remotelyjobs.databinding.FragmentCustomWebViewBinding
import com.eslammongy.remotelyjobs.databinding.FragmentSavedJobsBinding
import com.eslammongy.remotelyjobs.model.JobEntity
import com.eslammongy.remotelyjobs.viewModel.RemoteViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.*

class SavedJobsFragment : Fragment() , SavedJobsAdapter.OnItemClickListener{
    private var _binding: FragmentSavedJobsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel:RemoteViewModel
    private lateinit var savedJobsAdapter: SavedJobsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedJobsBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as HomeActivity).mainViewModel
        displayRecyclerView()


    }

    private fun displayRecyclerView(){

        savedJobsAdapter = SavedJobsAdapter(requireActivity() , this)
        binding.rvSavedJobs.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = savedJobsAdapter
        }

        viewModel.getAllSavedJobs().observe(viewLifecycleOwner , {favJob ->
            savedJobsAdapter.differ.submitList(favJob)
           // updatingRecyclerView(favJob)
        })
    }

    override fun onItemClick(jobEntity: JobEntity, view: View, position: Int) {
        showDeleteAlertDialog(jobEntity)
    }

    private fun showDeleteAlertDialog(jobEntity: JobEntity){

        AlertDialog.Builder(requireActivity()).apply {
            setTitle("Delete")

            setMessage("Are you sure you want to delete this job")
            setPositiveButton("Delete"){_ , _ ->
                viewModel.deleteFavJob(jobEntity)
                Toast.makeText(requireContext(), "Job Deleted ", Toast.LENGTH_SHORT).show()
            }
            setNegativeButton("Cancel" , null)
        }.create().show()

    }

//    private fun updatingRecyclerView(jobs: List<JobEntity>){
//
//        if (jobs.isNotEmpty()){
//
//        }else{
//
//        }
//
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }




}