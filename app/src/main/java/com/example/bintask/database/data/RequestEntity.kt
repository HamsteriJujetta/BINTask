package com.example.bintask.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bintask.base.REQUEST_TABLE

@Entity(tableName = REQUEST_TABLE)
class RequestEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "BIN")
    val BIN: String,
    @ColumnInfo(name = "requestDateTime")
    val requestDateTime: String
)