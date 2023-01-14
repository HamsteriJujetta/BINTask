package com.example.bintask.database.data

import com.example.bintask.database.data.RequestDao
import com.example.bintask.database.data.RequestEntity

class RequestLocalSource(private val requestDao: RequestDao) {

    suspend fun create(entity: RequestEntity) {
        requestDao.create(entity)
    }

    suspend fun read(): List<RequestEntity> {
        return requestDao.read()
    }

    suspend fun update(entity: RequestEntity) {
        requestDao.update(entity)
    }

    suspend fun delete(entity: RequestEntity) {
        requestDao.delete(entity)
    }

    suspend fun clearDB() {
        requestDao.clearDB()
    }

}