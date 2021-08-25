package com.eslammongy.remotelyjobs.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eslammongy.remotelyjobs.HomeActivity
import com.eslammongy.remotelyjobs.adapter.SavedJobsAdapter
import com.eslammongy.remotelyjobs.databinding.FragmentSavedJobsBinding
import com.eslammongy.remotelyjobs.model.JobEntity
import com.eslammongy.remotelyjobs.viewModel.RemoteViewModel
import com.google.android.material.snackbar.Snackbar

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

        var deletedItem: String?
        val itemTouchHelperCallback =
            object :
                ItemTouchHelper.SimpleCallback(
                    0,
                    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false

                }
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    val position: Int = viewHolder.adapterPosition
                    val listSavedJobs: JobEntity = savedJobsAdapter.differ.currentList[position]

                    context?.let {
                        viewModel.deleteFavJob(listSavedJobs)
                    }
                    deletedItem =
                        "Are You Sure You Want To Delete This Job OR Undo Deleted .."
                    viewModel.deleteFavJob(listSavedJobs)
                   // savedJobsAdapter.differ.currentList.remove(listSavedJobs)
                    savedJobsAdapter.notifyDataSetChanged()
                    Snackbar.make(binding.rvSavedJobs, deletedItem!!, Snackbar.LENGTH_LONG)
                        .setAction(
                            "Undo"
                        ) {

//                            savedJobsAdapter.differ.currentList.add(position , listSavedJobs)
                            viewModel.insertNewJob(listSavedJobs)
                            savedJobsAdapter.notifyItemInserted(position)

                        }.show()
                }
            }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvSavedJobs)


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