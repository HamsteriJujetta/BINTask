package com.example.bintask.database.di

import androidx.room.Room
import com.example.bintask.AppDatabase
import com.example.bintask.base.APP_DATABASE
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room
            .databaseBuilder(androidContext(), AppDatabase::class.java, APP_DATABASE)
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        get<AppDatabase>().requestDao()
    }
}