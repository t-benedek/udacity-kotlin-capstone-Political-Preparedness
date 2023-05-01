package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.android.politicalpreparedness.App
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.database.ElectionRepo
import com.example.android.politicalpreparedness.databinding.FragmentElectionDetailsBinding
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.*

private const val LOGTAG = "ElectionDetail"
class ElectionDetailsFragment : Fragment() {

    private val electionDao = ElectionDatabase.getInstance(App.context).electionDao
    private val repo = ElectionRepo(electionDao)

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val binding = FragmentElectionDetailsBinding.inflate(inflater)
            binding.lifecycleOwner = this

            return binding.root
        }




}