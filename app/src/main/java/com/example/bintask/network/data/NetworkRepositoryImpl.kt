package com.example.bintask.network.data

import android.util.Log
import com.example.bintask.network.data.models.BINInfoModel

class NetworkRepositoryImpl(private val source: NetworkRemoteSource) : NetworkRepository {

    override suspend fun getBINInfo(BIN: String): BINInfoModel {
        return source.getBINInfo(BIN)
    }

}