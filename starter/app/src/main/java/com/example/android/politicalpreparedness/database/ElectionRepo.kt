package com.example.android.politicalpreparedness.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.parseElectionJsonResult
import org.json.JSONObject

private const val LOGTAG = "REPO"

class ElectionRepo(private val database: ElectionDao) {
    val elections = database.getAllElections()

    val API_KEY = "AIzaSyATWja6yd8IwqTpBf8Y4q0O3cqN8zLnwd8"

    suspend fun refreshElections() {
        try {
            Log.i(LOGTAG, "Refresh Elections")
            val response = CivicsApi.retrofitService.getAllElections(API_KEY)
            val jsonObject = JSONObject(response)
            val list = parseElectionJsonResult(jsonObject)
            for (a in list) {
                database.insert(a)
                Log.i(LOGTAG, "Adding Election with name " + a.name)
            }

        } catch (ex: java.lang.Exception) {
            Log.e(LOGTAG,ex.message,ex)
        }
    }
}