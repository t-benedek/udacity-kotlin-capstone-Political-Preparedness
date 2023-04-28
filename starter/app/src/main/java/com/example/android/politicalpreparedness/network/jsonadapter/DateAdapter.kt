package com.example.android.politicalpreparedness.network.jsonadapter

import com.squareup.moshi.*
import java.text.SimpleDateFormat
import java.util.*

class DateAdapter {

    companion object {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US);
    }

    @FromJson
    fun dateFromJson(date: String): Date? {
        return dateFormat.parse(date)
    }

    @ToJson
    fun dateToJson(date: Date): String {
        return dateFormat.format(date)
    }

}