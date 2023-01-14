package com.example.bintask.database.data

import com.example.bintask.base.Either
import com.example.bintask.base.attempt


// class for interacting with database (table with previous requests information)

class RequestInteractor(private val requestRepository: RequestRepository) {

    suspend fun create(model: RequestModel) {
        attempt { requestRepository.create(model) }
    }

    suspend fun read(): Either<Throwable, List<RequestModel>> {
        return attempt { requestRepository.read() }
    }

    suspend fun update(model: RequestModel) {
        attempt { requestRepository.update(model) }
    }

    suspend fun delete(model: RequestModel) {
        attempt { requestRepository.delete(model) }
    }

    suspend fun clearDB() {
        attempt { requestRepository.clearDB() }
    }
}