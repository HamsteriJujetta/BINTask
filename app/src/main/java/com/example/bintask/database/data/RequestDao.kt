package com.example.bintask.database.data

import com.example.bintask.base.REQUEST_TABLE
import androidx.room.*

@Dao
interface RequestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun create(entity: RequestEntity)

    @Query("SELECT * FROM $REQUEST_TABLE")
    suspend fun read(): List<RequestEntity>

    @Update
    suspend fun update(entity: RequestEntity)

    @Delete
    suspend fun delete(entity: RequestEntity)

    @Query("DELETE FROM $REQUEST_TABLE")
    suspend fun clearDB()

}