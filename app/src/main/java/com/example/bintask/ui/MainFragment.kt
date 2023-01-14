package com.example.bintask.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.bintask.R
import com.example.bintask.base.ViewState
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by activityViewModel()

    private val btnLoadBINInfo: Button by lazy { requireActivity().findViewById(R.id.btnLoadBINInfo) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewState.observe(viewLifecycleOwner, ::render)

        btnLoadBINInfo.setOnClickListener {
            // TODO: viewModel.load()
        }
    }

    private fun render(viewState: ViewState) {
        if (viewState.isError) {
            Toast.makeText(
                requireActivity().applicationContext,
                "Failed to load previous requests",
                Toast.LENGTH_LONG
            ).show()
        } else {
            //adapter.setData(viewState.tasks)
        }
    }

}