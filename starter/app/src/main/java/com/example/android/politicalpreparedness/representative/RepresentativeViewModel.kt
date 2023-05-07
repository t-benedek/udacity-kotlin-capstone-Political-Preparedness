package com.example.android.politicalpreparedness.representative

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.App
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.database.ElectionRepo
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.network.models.RepresentativeResponse
import com.example.android.politicalpreparedness.representative.model.Representative
import kotlinx.coroutines.launch
import com.example.android.politicalpreparedness.utils.ProgressState
import com.example.android.politicalpreparedness.utils.ProgressState.*

class RepresentativeViewModel : ViewModel() {


    private val electionDao = ElectionDatabase.getInstance(App.context).electionDao
    private val repo = ElectionRepo(electionDao)

    companion object {
        const val TAG = "RepresentativeViewModel"
    }

    var representativeResponse: RepresentativeResponse? = null

    // The internal MutableLiveData that holds the data to be displayed on the UI
    private val _representativesList = MutableLiveData<List<Representative>>()

    // The external immutable LiveData
    val representativesList: LiveData<List<Representative>>
        get() = _representativesList

    // The internal MutableLiveData to store state value that changes when user clicks on search button,
    // and when API call is complete. Used for progress bar, text info and recycler view visibility
    private val _currentSearchState = MutableLiveData<ProgressState>()

    // The external immutable LiveData
    val currentSearchState: LiveData<ProgressState>
        get() = _currentSearchState

    // The MutableLiveData that holds the data to be displayed on the UI
    private val _locationAddress = MutableLiveData<Address>()
    val locationAddress: LiveData<Address>
        get() = _locationAddress

    init {
        _currentSearchState.value = INITIAL
    }

    fun searchRepresentatives(address: String) {
        viewModelScope.launch {
            _currentSearchState.value = LOADING_ACTIVE
            try {
                representativeResponse = repo.getRepresentatives(address)
                setRepresentativesList()
                _currentSearchState.value = LOADING_SUCCESS
            } catch (e: Exception) {
                _currentSearchState.value = LOADING_FAILURE
                Log.d(TAG, e.printStackTrace().toString())
            }

        }
    }

    private fun setRepresentativesList() {
        if (representativeResponse != null) {
            _representativesList.value = representativeResponse!!.offices.flatMap { office ->
                office.getRepresentatives(representativeResponse!!.officials)
            }
        }
    }

    fun fillInAddressForm(address: Address?) {
        _locationAddress.value = address
    }

    fun onRestoreSearchProgressState(searchState: ProgressState?) {
        _currentSearchState.value = searchState
    }

    fun onRestoreRecyclerViewData(recyclerViewData: RepresentativeResponse?) {
        if (recyclerViewData != null) {
            representativeResponse = recyclerViewData
            setRepresentativesList()
        }
    }
}