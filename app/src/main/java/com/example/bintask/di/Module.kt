package com.example.bintask.di

import com.example.bintask.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {

    viewModel {
        MainViewModel()
    }
}