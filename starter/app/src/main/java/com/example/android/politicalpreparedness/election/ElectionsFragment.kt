package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter

private const val LOGTAG = "ElectionFrag"

class ElectionsFragment: Fragment() {

    private val viewModel by viewModels<ElectionsViewModel>()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = FragmentElectionBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        val upComingAdapter = ElectionListAdapter(ElectionListAdapter.ElectionListener { electionID ->
            viewModel.onElectionClicked(electionID)
        })

        val savedAdapter = ElectionListAdapter(ElectionListAdapter.ElectionListener { electionID ->
            viewModel.onElectionClicked(electionID)
        })

        viewModel.elections.observe(viewLifecycleOwner, Observer {
            it?.let {
                upComingAdapter.submitList(it)
            }
        })

        viewModel.saved_elections.observe(viewLifecycleOwner, Observer {
            it?.let {
                savedAdapter.submitList(it)
            }
        })

        viewModel.navigateToElectionDetails.observe(viewLifecycleOwner) { election ->
            election?.let {

                findNavController()
                    .navigate(ElectionsFragmentDirections
                        .actionElectionsFragmentToVoterInfoFragment(election.id, election.division, election.name))

                //tell the fragment that navigation was done
                this.viewModel.onElectionDetailNavigated()
            }
        }

        binding.upcomingElectionsRecycler.adapter = upComingAdapter
        binding.savedElectionsRecycler.adapter = savedAdapter

        return binding.root
    }

}