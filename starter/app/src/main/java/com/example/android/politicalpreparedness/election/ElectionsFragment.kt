package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.databinding.FragmentLaunchBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter

class ElectionsFragment: Fragment() {

    //TODO: Declare ViewModel
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

        binding.upcomingElectionsRecycler.adapter = adapter

        //TODO: Populate recycler adapters

        return binding.root
    }

    /// TODO: Refresh adapters when fragment loads

}