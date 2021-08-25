package com.eslammongy.remotelyjobs.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.eslammongy.remotelyjobs.HomeActivity
import com.eslammongy.remotelyjobs.adapter.RemoteJobsAdapter
import com.eslammongy.remotelyjobs.databinding.FragmentDesignBinding
import com.eslammongy.remotelyjobs.other.Constants
import com.eslammongy.remotelyjobs.viewModel.RemoteViewModel

class DesignFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var _binding:FragmentDesignBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RemoteViewModel
    private lateinit var remotelyJobsAdapter: RemoteJobsAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentDesignBinding.inflate(inflater , container , false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as HomeActivity).mainViewModel
        swipeRefresh = binding.swipeRefresh
        swipeRefresh.setOnRefreshListener(this)
        swipeRefresh.setColorSchemeColors(Color.GREEN , Color.RED, Color.BLUE , Color.DKGRAY)
        swipeRefresh.post {
            swipeRefresh.isRefreshing = true
            displayDevopsRecyclerView()
        }
        binding.progressCircular.visibility = View.VISIBLE
        if (Constants.checkNetworkConnection(requireContext())){
            displayDevopsRecyclerView()
        }else{
            swipeRefresh.isRefreshing = false
            binding.progressCircular.visibility = View.GONE
            Toast.makeText(requireContext(), "No Internet Connection !!", Toast.LENGTH_SHORT).show()
        }

    }

    private fun displayDevopsRecyclerView(){

        remotelyJobsAdapter = RemoteJobsAdapter(requireActivity())
        binding.rvGraphicDesigner.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = remotelyJobsAdapter
        }
        fetchingData()
    }

    private fun fetchingData(){

        viewModel.remoteGraphicJobsResult().observe(viewLifecycleOwner , {remoteJobs->
            if (remoteJobs != null){
                binding.progressCircular.visibility = View.GONE
                remotelyJobsAdapter.differ.submitList(remoteJobs.jobs)
            }

        })
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onRefresh() {
        displayDevopsRecyclerView()
    }

}