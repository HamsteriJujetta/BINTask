package com.example.bintask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bintask.ui.MainFragment
import com.example.bintask.ui.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, MainFragment())
                .commit()
        }
    }
}