package com.eslammongy.remotelyjobs.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.eslammongy.remotelyjobs.HomeActivity
import com.eslammongy.remotelyjobs.R
import com.eslammongy.remotelyjobs.adapter.RemoteJobsAdapter
import com.eslammongy.remotelyjobs.databinding.FragmentMarktingBinding
import com.eslammongy.remotelyjobs.other.Constants
import com.eslammongy.remotelyjobs.viewModel.RemoteViewModel

class MarketingFragment : Fragment() {

    private var _binding:FragmentMarktingBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RemoteViewModel
    private lateinit var remotelyJobsAdapter: RemoteJobsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMarktingBinding.inflate(inflater , container , false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as HomeActivity).mainViewModel
        if (Constants.checkNetworkConnection(requireContext())){
            displayDevopsRecyclerView()
        }else{
            Toast.makeText(requireContext(), "No Internet Connection !!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayDevopsRecyclerView(){

        remotelyJobsAdapter = RemoteJobsAdapter(requireActivity())
        binding.rvMarketing.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = remotelyJobsAdapter
        }
        fetchingData()
    }

    private fun fetchingData(){

        viewModel.remoteMarketingJobsResult().observe(viewLifecycleOwner , {remoteJobs->

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