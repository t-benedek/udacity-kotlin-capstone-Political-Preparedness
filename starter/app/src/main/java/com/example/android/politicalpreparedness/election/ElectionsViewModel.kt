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

private const val TAG = "ELECTION_VIEWMODEL"
class ElectionsViewModel(): ViewModel() {

    private val electionDao = ElectionDatabase.getInstance(App.context).electionDao
    private val repo = ElectionRepo(electionDao)
    private val application = App.context

    private var _navigateToElectionDetail = MutableLiveData<Election>()
    val navigateToElectionDetails:LiveData<Election?>
            get() = _navigateToElectionDetail

    private var _elections = MutableLiveData<List<Election>>()
    val elections:LiveData<List<Election>?>
        get() = _elections

    lateinit var saved_elections:LiveData<List<Election>?>

    init {
        _navigateToElectionDetail.value = null
        loadSavedElections()
        loadAllElections()
    }

    private fun loadAllElections() {
        viewModelScope.launch {
            try {
                if (isNetworkAvailable()) {
                    _elections.value = repo.getAllElections()
                    Log.i(TAG, "First Election is " + repo.getAllElections().get(0).name)
                }
            } catch (e: java.lang.Exception) {
                Log.e("ElectionsViewModel", "exception thrown: ${e.localizedMessage}")
            }
        }
    }

    fun loadSavedElections() {
        viewModelScope.launch {
            try {
                saved_elections = repo.getSavedElections()

            } catch (e: Exception) {
                Log.d(TAG, e.printStackTrace().toString())
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

    fun onElectionDetailNavigated(){
        _navigateToElectionDetail.value = null
    }

    private fun isNetworkAvailable(): Boolean {
        val connManager = App.context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities =  connManager.getNetworkCapabilities(connManager.activeNetwork)
        return networkCapabilities != null
    }
}