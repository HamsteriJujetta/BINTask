package com.example.bintask.network.data.models

import com.google.gson.annotations.SerializedName

data class BINBank(
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("city")
    val city: String?
)