package com.eslammongy.remotelyjobs.fragments

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.webkit.WebViewClient
import androidx.fragment.app.DialogFragment
import com.eslammongy.remotelyjobs.HomeActivity
import com.eslammongy.remotelyjobs.R
import com.eslammongy.remotelyjobs.databinding.FragmentCustomWebViewBinding
import com.eslammongy.remotelyjobs.model.JobEntity
import com.eslammongy.remotelyjobs.model.JobModel
import com.eslammongy.remotelyjobs.viewModel.RemoteViewModel
import com.google.android.material.snackbar.Snackbar

class CustomWebView(private var currentJob: JobModel) : DialogFragment() {
    private var _binding: FragmentCustomWebViewBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RemoteViewModel

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomWebViewBinding.inflate(inflater, container, false)
        dialog!!.window!!.setWindowAnimations(R.style.AnimationDialog)

        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as HomeActivity).mainViewModel
        binding.taskWebView.loadUrl(currentJob.url!!)
        val webViewSetting = binding.taskWebView.settings
        webViewSetting.javaScriptEnabled = true
        binding.taskWebView.webViewClient = WebViewClient()
        binding.taskWebView.canGoBack()
        binding.taskWebView.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == MotionEvent.ACTION_UP && binding.taskWebView.canGoBack()) {
                binding.taskWebView.goBack()
                return@OnKeyListener true
            }
            return@OnKeyListener false
        })
        binding.btnExitWebView.setOnClickListener {
            dialog!!.dismiss()
        }

        binding.btnSavedFavJobs.setOnClickListener {
            saveNewFavJobs()
        }
        binding.tvShowLinkInWebView.text = currentJob.title
    }

    private fun saveNewFavJobs() {

        val favJob = JobEntity(
            0,
            currentJob.candidateRequiredLocation, currentJob.category, currentJob.companyLogoUrl,
            currentJob.companyName, currentJob.description, currentJob.id, currentJob.jobType,
            currentJob.publicationDate, currentJob.salary, currentJob.title, currentJob.url
        )
        viewModel.insertNewJob(favJob)
        Snackbar.make(binding.root, "Your Fav Job Saved Successfully", Snackbar.LENGTH_LONG).apply {
            setBackgroundTint(Color.DKGRAY)
            show()
        }
    }

    override fun onStart() {
        super.onStart()
        val sheetContainer = requireView().parent as? ViewGroup ?: return
        val width = (resources.displayMetrics.widthPixels)
        val height = (resources.displayMetrics.heightPixels)
        sheetContainer.layoutParams.width = width
        sheetContainer.layoutParams.height = height
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        sheetContainer.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}