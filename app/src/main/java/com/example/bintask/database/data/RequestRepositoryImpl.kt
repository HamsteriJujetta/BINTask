package com.example.bintask.database.data

class RequestRepositoryImpl(private val requestLocalSource: RequestLocalSource) :
    RequestRepository {
    override suspend fun create(model: RequestModel) {
        requestLocalSource.create(model.toEntity())
    }

    override suspend fun read(): List<RequestModel> {
        return requestLocalSource.read().map { it.toDomain() }
    }

    override suspend fun update(model: RequestModel) {
        requestLocalSource.update(model.toEntity())
    }

    override suspend fun delete(model: RequestModel) {
        requestLocalSource.delete(model.toEntity())
    }

    override suspend fun clearDB() {
        requestLocalSource.clearDB()
    }
}