package com.example.bintask.network.data

import com.example.bintask.network.data.models.BINInfoModel
import retrofit2.http.GET
import retrofit2.http.Url

interface BINApi {

    @GET
    suspend fun getBINInfo(
        @Url BIN: String
    ) : BINInfoModel

}