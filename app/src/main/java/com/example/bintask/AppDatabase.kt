package com.example.bintask

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bintask.database.data.RequestDao
import com.example.bintask.database.data.RequestEntity

@Database(entities = [RequestEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun requestDao(): RequestDao

}