package com.example.android.politicalpreparedness.network.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RepresentativeResponse(
        val offices: List<Office>,
        val officials: List<Official>
): Parcelable