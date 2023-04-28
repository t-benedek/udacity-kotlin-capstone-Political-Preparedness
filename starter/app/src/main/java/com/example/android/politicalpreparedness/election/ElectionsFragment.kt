package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.databinding.FragmentLaunchBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.network.models.Election

private const val LOGTAG = "ElectionFrag"

class ElectionsFragment: Fragment() {

    private val viewModel by viewModels<ElectionsViewModel>()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = FragmentElectionBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        //TODO: Link elections to voter info

        //TODO: Initiate recycler adapters
        val application = requireNotNull(this.activity).application
        val adapter = ElectionListAdapter(ElectionListAdapter.ElectionListener { electionID ->
            viewModel.onElectionClicked(electionID)
        })

        viewModel.elections.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
                Log.i(LOGTAG, "Update Viewmodel " + it.toString())
            }
        })

        viewModel.navigateToElectionDetails.observe(viewLifecycleOwner) { election ->
            election?.let {

                findNavController().navigate(ElectionsFragmentDirections.actionElectionsFragmentToElectionsDetailFragment())

                //tell the fragment that navigation was done
                this.viewModel.onElectionDetailNavigated()
            }
        }

        //TODO: Populate recycler adapters
        binding.upcomingElectionsRecycler.adapter = adapter
        return binding.root
    }

    /// TODO: Refresh adapters when fragment loads

}