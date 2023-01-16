package com.example.bintask.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.bintask.MainActivity
import com.example.bintask.R
import com.example.bintask.base.UiEvent
import com.example.bintask.base.ViewState
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class RequestsFragment() : Fragment(R.layout.fragment_requests) {

    private val viewModel: MainViewModel by activityViewModel()
    private val rvRequests: RecyclerView by lazy { requireActivity().findViewById(R.id.rvRequests) }
    private val btnGoBack: Button by lazy { requireActivity().findViewById(R.id.btnGoBack) }
    private val btnDeleteRequests: Button by lazy { requireActivity().findViewById(R.id.btnDeleteRequests) }

    private val requestAdapter: RequestAdapter by lazy {
        RequestAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvRequests.adapter = requestAdapter
        viewModel.viewState.observe(viewLifecycleOwner, ::render)

        btnGoBack.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, MainFragment())
                .commit()
        }

        btnDeleteRequests.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnDeleteAllRequestsClicked)
        }
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
    }

}