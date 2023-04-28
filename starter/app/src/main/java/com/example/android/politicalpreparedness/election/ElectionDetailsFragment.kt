package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android.politicalpreparedness.databinding.FragmentElectionDetailsBinding

class ElectionDetailsFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val binding = FragmentElectionDetailsBinding.inflate(inflater)
            binding.lifecycleOwner = this

            // TODO Add binding
            // binding.election = ele
            return binding.root
        }
}