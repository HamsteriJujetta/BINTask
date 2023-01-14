package com.example.bintask.database.data

fun RequestEntity.toDomain() = RequestModel(
    id = id,
    BIN = BIN,
    requestDateTime = requestDateTime
)

fun RequestModel.toEntity() = RequestEntity(
    id = id,
    BIN = BIN,
    requestDateTime = requestDateTime
)