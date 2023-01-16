package com.example.bintask.network.data.models

import com.google.gson.annotations.SerializedName

data class BINInfoModel(
    @SerializedName("number")
    val number: BINNumber?,
    @SerializedName("scheme")
    val scheme: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("brand")
    val brand: String?,
    @SerializedName("prepaid")
    val prepaid: Boolean?,
    @SerializedName("country")
    val country: BINCountry?,
    @SerializedName("bank")
    val bank: BINBank?
)