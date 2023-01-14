package com.example.bintask.network.data.models

import com.google.gson.annotations.SerializedName

data class BINNumber(
    @SerializedName("length")
    val length: Int,
    @SerializedName("luhn")
    val luhn: Boolean
)