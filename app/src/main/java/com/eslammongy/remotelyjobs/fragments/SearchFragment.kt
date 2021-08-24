package com.eslammongy.remotelyjobs.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.eslammongy.remotelyjobs.HomeActivity
import com.eslammongy.remotelyjobs.adapter.RemoteJobsAdapter
import com.eslammongy.remotelyjobs.databinding.FragmentSearchBinding
import com.eslammongy.remotelyjobs.other.Constants.checkNetworkConnection
import com.eslammongy.remotelyjobs.viewModel.RemoteViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.NullPointerException

class SearchFragment : Fragment() {
    private var _binding:FragmentSearchBinding? = null
    private val binding get() =  _binding!!
    private lateinit var viewModel:RemoteViewModel
    private lateinit var searchAdapter:RemoteJobsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as HomeActivity).mainViewModel

        if (checkNetworkConnection(requireContext())){
            fillSearchResult()
        }else{
            Toast.makeText(requireContext(), "No Internet Connection !!", Toast.LENGTH_SHORT).show()
        }

       // displayRecyclerView()

    }

    private fun fillSearchResult(){

        var job:Job? = null
        binding.btnSearchForJobs.addTextChangedListener { text ->
            job?.cancel()
            job = MainScope().launch {
                delay(700L)
                text?.let {
                    if (text.toString().isNotEmpty()){
                        viewModel.searchForJobs(text.toString())
                    }
                }
            }
        }
        displayRecyclerView()

    }

    private fun displayRecyclerView(){
        searchAdapter = RemoteJobsAdapter(requireActivity())
        binding.rvSearchedResult.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = searchAdapter
        }
        viewModel.searchJobsResult().observe(viewLifecycleOwner , {searchResult ->
            try {
                searchAdapter.differ.submitList(searchResult.jobs!!)
            }catch (e:NullPointerException){
                Toast.makeText(requireActivity(), "${e.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}