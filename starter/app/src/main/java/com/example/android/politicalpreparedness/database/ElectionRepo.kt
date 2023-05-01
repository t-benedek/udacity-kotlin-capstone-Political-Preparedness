package com.example.android.politicalpreparedness.database

import androidx.lifecycle.LiveData
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
private const val LOGTAG = "REPO"

class ElectionRepo(private val database: ElectionDao) {

    val API_KEY = "AIzaSyATWja6yd8IwqTpBf8Y4q0O3cqN8zLnwd8"
    lateinit var elections: List<Election>

    suspend fun saveElection(el: Election) {
        database.insert(el)
    }

    suspend fun deleteElection(id: Int) {
        database.deleteElection(id)
    }

    suspend fun getElection(id:Int): Election? {
        return database.getElection(id)
    }

    suspend fun getAllElections(): List<Election> {
        elections = CivicsApi.retrofitService.getElections().elections
        return  elections
    }

    suspend fun getSavedElections(): LiveData<List<Election>?> {
        return database.getAllElections()
    }

    suspend fun clearDatabase() {
        database.clearElections()
    }


}