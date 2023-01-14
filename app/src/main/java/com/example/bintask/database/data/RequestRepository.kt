package com.example.bintask.database.data

interface RequestRepository {

    suspend fun create(model: RequestModel)
    suspend fun read(): List<RequestModel>
    suspend fun update(model: RequestModel)
    suspend fun delete(model: RequestModel)
    suspend fun clearDB()

}