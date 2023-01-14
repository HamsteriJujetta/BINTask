package com.example.bintask.network.data

import com.example.bintask.network.data.models.BINInfoModel

interface NetworkRepository {
    suspend fun getBINInfo(BIN: String): BINInfoModel
}