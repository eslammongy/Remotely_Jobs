package com.eslammongy.remotelyjobs.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.eslammongy.remotelyjobs.HomeActivity
import com.eslammongy.remotelyjobs.adapter.RemoteJobsAdapter
import com.eslammongy.remotelyjobs.databinding.FragmentSoftEngineerBinding
import com.eslammongy.remotelyjobs.viewModel.RemoteViewModel

class SoftEngineerFragment : Fragment() {

    private var _binding:FragmentSoftEngineerBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel:RemoteViewModel
    private lateinit var remotelyJobsAdapter: RemoteJobsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSoftEngineerBinding.inflate(inflater , container , false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as HomeActivity).mainViewModel
        displayDevopsRecyclerView()
    }

    private fun displayDevopsRecyclerView(){

        remotelyJobsAdapter = RemoteJobsAdapter(requireActivity())
        binding.rvSoftDevelopment.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(object :DividerItemDecoration(activity , LinearLayout.HORIZONTAL){})
            adapter = remotelyJobsAdapter
        }
        fetchingData()
    }

    private fun fetchingData(){

        viewModel.remoteSoftwareJobsResult().observe(viewLifecycleOwner , {remoteJobs->

            if (remoteJobs != null){
                remotelyJobsAdapter.differ.submitList(remoteJobs.jobs)
            }

        })
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}