package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.android.politicalpreparedness.App
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.database.ElectionRepo
import com.example.android.politicalpreparedness.databinding.FragmentElectionDetailsBinding

class ElectionDetailsFragment : Fragment() {

    private val electionDao = ElectionDatabase.getInstance(App.context).electionDao
    private val repo = ElectionRepo(electionDao)

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val binding = FragmentElectionDetailsBinding.inflate(inflater)
            binding.lifecycleOwner = this

            // TODO Add binding
            val list = repo.elections.value
            var el = list?.get(0)
            val elName = ElectionDetailsFragmentArgs.fromBundle(arguments!!).electionName
            val elDate = ElectionDetailsFragmentArgs.fromBundle(arguments!!).electionDate
            val elDivision = ElectionDetailsFragmentArgs.fromBundle(arguments!!).division

            binding.electionName = elName
            binding.electionDate = elDate
            binding.division = elDivision
            return binding.root
        }
}