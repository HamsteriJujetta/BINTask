package com.example.bintask.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.bintask.R
import com.example.bintask.base.UiEvent
import com.example.bintask.base.ViewState
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by activityViewModel()

    private val btnLoadBINInfo: Button by lazy { requireActivity().findViewById(R.id.btnLoadBINInfo) }
    private val etBIN: EditText by lazy { requireActivity().findViewById(R.id.etBIN) }
    private val rvRequests: RecyclerView by lazy { requireActivity().findViewById(R.id.rvRequests) }
    private val requestAdapter: RequestAdapter by lazy {
        RequestAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewState.observe(viewLifecycleOwner, ::render)

        btnLoadBINInfo.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnLoadBINInfoClicked(etBIN.text.toString()))
        }

        rvRequests.adapter = requestAdapter
    }

    private fun render(viewState: ViewState) {
        if (viewState.errorOnRequests) {
            Toast.makeText(
                requireActivity().applicationContext,
                "Failed to load previous requests",
                Toast.LENGTH_LONG
            ).show()
        } else {
            requestAdapter.setData(viewState.prevRequests.reversed())
        }

        if (viewState.errorOnBINInfo) {
            Toast.makeText(
                requireActivity().applicationContext,
                "Failed to get BIN information",
                Toast.LENGTH_LONG
            ).show()
        } else {
            //Log.d("Hamster render", "${viewState.binInfo.toString()}") // TODO
        }

        etBIN.setText(viewState.BIN)
    }

}