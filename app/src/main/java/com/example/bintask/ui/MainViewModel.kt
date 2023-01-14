package com.example.bintask.ui

import androidx.lifecycle.viewModelScope
import com.example.bintask.base.DataEvent
import com.example.bintask.base.Event
import com.example.bintask.base.UiEvent
import com.example.bintask.base.ViewState
import com.example.bintask.database.data.RequestInteractor
import com.example.urgenttask.base.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel(
    private val requestInteractor: RequestInteractor // for interacting with database
) : BaseViewModel<ViewState>() {

    init {
        //processDataEvent(DataEvent.OnLoadPreviousRequests)
    }

    override fun initialViewState(): ViewState =
        ViewState(BIN = "", prevRequests = emptyList(), isError = false)

    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {

            // interact with API, get information about new BIN
            is UiEvent.OnLoadBINInfoClicked -> {
                return previousState.copy(BIN = event.bin)
            }

            // interact with database, get information about previous requests
            is DataEvent.OnLoadPreviousRequests -> {
                viewModelScope.launch {
                    requestInteractor.read().fold(
                        onError = {
                            processDataEvent(DataEvent.OnLoadPreviousRequestsFail)
                        },
                        onSuccess = {
                            processDataEvent(DataEvent.OnLoadPreviousRequestsSuccess(it))
                        }
                    )
                }
                return null
            }
            is DataEvent.OnLoadPreviousRequestsFail -> {
                return previousState.copy(isError = true)
            }
            is DataEvent.OnLoadPreviousRequestsSuccess -> {
                return previousState.copy(prevRequests = event.requests)
            }

            else -> return null
        }
    }
}