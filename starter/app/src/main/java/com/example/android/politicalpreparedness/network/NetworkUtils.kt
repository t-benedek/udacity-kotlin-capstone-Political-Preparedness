package com.example.android.politicalpreparedness.network

import com.example.android.politicalpreparedness.network.models.Division
import com.example.android.politicalpreparedness.network.models.Election
import org.json.JSONObject
import java.util.*


    fun parseElectionJsonResult(jsonObject: JSONObject): List<Election> {
        val electionList = mutableListOf<Election>()

        val electionObjectsJson = jsonObject.getJSONArray("elections")
        val dateList = listOf(electionObjectsJson)
        dateList.forEach {
            for (i in 0 until electionObjectsJson.length()) {
                val electionJson = electionObjectsJson.getJSONObject(i)
                val id = electionJson.getInt("id")
                val name = electionJson.getString("name")
                val election = Election (
                    id,
                    name,
                    Date(1-1-2023),
                    Division("DivID", "Germany", "Bayern")
                )
                electionList.add(election)
            }
        }
        return electionList
    }