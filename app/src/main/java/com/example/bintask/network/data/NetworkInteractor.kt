package com.example.bintask.network.data

import com.example.bintask.base.attempt

class NetworkInteractor(private val repository: NetworkRepository) {
    suspend fun getBINInfo(BIN: String) = attempt {
        repository.getBINInfo(BIN)
    }
}