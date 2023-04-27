package com.example.android.politicalpreparedness.election

import android.app.Application
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.App
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.database.ElectionRepo
import com.example.android.politicalpreparedness.network.models.Division
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.launch
import java.sql.Date

class ElectionsViewModel(): ViewModel() {

    private val electionDao = ElectionDatabase.getInstance(App.context).electionDao
    private val repo = ElectionRepo(electionDao)
    private val application = App.context

    private var _navigateToElectionDetail = MutableLiveData<Election>()
    private val navigateToElectionDetails:LiveData<Election?>
            get() = _navigateToElectionDetail

    var elections:LiveData<List<Election>?> = repo.elections


    //TODO: Create live data val for saved elections

    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database

    //TODO: Create functions to navigate to saved or upcoming election voter info
    init {
        _navigateToElectionDetail.value = null
        val el1 = Election(1, "Test", Date(1-1-2023), Division("1", "GER", "BY"))
        val list =  listOf(el1)

        viewModelScope.launch {
            try {
                if (isNetworkAvailable())  repo.refreshElections()
            } catch (e:java.lang.Exception) {
                Log.e("ElectionsViewModel", "exception thrown: ${e.localizedMessage}")
            }
        }
    }
    fun onElectionClicked(electionID: Int) {
        viewModelScope.launch {
            var election = elections.value?.get(0)
            for (e in elections.value!!) {
                if (e.id == electionID) election = e
            }
            _navigateToElectionDetail.value = election
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connManager = App.context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities =  connManager.getNetworkCapabilities(connManager.activeNetwork)
        return networkCapabilities != null
    }
}