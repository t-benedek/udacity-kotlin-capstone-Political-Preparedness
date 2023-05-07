package com.example.android.politicalpreparedness.database

import androidx.lifecycle.LiveData
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.RepresentativeResponse
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse

private const val LOGTAG = "REPO"

class ElectionRepo(private val database: ElectionDao) {

    val API_KEY = "AIzaSyATWja6yd8IwqTpBf8Y4q0O3cqN8zLnwd8"
    lateinit var elections: List<Election>

    suspend fun saveElection(el: Election): Long? {
        val insertId = database?.insert(el)
        return insertId
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

    suspend fun getVoterInfo(electionId: Int, electionName: String): VoterInfoResponse {
        return CivicsApi.retrofitService.getVoterInfo (
            mapOf(
                "electionId" to electionId.toString(),
                "address" to electionName
            )
        )
    }

    suspend fun getRepresentatives(address: String): RepresentativeResponse {
        return CivicsApi.retrofitService.getRepresentatives(address)
    }


}