package com.eslammongy.remotelyjobs.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.eslammongy.remotelyjobs.R
import com.eslammongy.remotelyjobs.databinding.FragmentJobDetailesBinding
import com.eslammongy.remotelyjobs.model.JobModel

class JobDetailsFragment : Fragment(R.layout.fragment_job_detailes) {

    private var _binding:FragmentJobDetailesBinding? = null
    private val binding get() = _binding!!
    private lateinit var currentJob:JobModel
    private val args:JobDetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
          _binding = FragmentJobDetailesBinding.inflate(inflater , container , false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentJob = args.jobModel!!
        displayWebView()
    }

    private fun displayWebView(){

        binding.jobWebView.apply {
            webViewClient = WebViewClient()
            currentJob.url?.let { loadUrl(it) }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}