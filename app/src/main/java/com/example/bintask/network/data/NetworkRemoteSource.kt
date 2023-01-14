package com.example.bintask.network.data

import com.example.bintask.network.data.models.BINInfoModel

class NetworkRemoteSource(private val api: BINApi) {

    suspend fun getBINInfo(BIN: String): BINInfoModel {
        return api.getBINInfo(BIN)
    }

}