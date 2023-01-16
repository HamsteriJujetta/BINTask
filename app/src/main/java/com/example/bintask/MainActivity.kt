package com.example.bintask

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bintask.ui.MainFragment
import com.example.bintask.ui.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CALL_PHONE = 1
    }

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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        //Log.d("Hamster", "onRequestPermissionsResult")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CALL_PHONE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                    //Log.d("Hamster", "permission granted")
                    //doThePhoneCall()
                    val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
                    if (fragment is MainFragment) {
                        fragment.doThePhoneCall()
                    } else {
                        //Log.d("Hamster", "failed to get current fragment")
                    }
                } else {
                    // permission denied
                    //Log.d("Hamster", "permission denied")
                    Toast.makeText(
                        this,
                        "Application needs your permission to make a call",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            else -> {
                Toast.makeText(
                    this,
                    "Unknown permission case",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

}