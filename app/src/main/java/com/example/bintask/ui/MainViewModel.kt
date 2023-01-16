package com.example.bintask.ui

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.bintask.base.DataEvent
import com.example.bintask.base.Event
import com.example.bintask.base.UiEvent
import com.example.bintask.base.ViewState
import com.example.bintask.database.data.RequestInteractor
import com.example.bintask.database.data.RequestModel
import com.example.bintask.network.data.NetworkInteractor
import com.example.bintask.network.data.models.BINBank
import com.example.bintask.network.data.models.BINCountry
import com.example.bintask.network.data.models.BINInfoModel
import com.example.bintask.network.data.models.BINNumber
import com.example.urgenttask.base.BaseViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(
    private val requestInteractor: RequestInteractor, // for interacting with database
    private val networkInteractor: NetworkInteractor // for getting information about BIN
) : BaseViewModel<ViewState>() {

    init {
        processDataEvent(DataEvent.OnLoadPreviousRequests)
    }

    override fun initialViewState(): ViewState =
        ViewState(
            BIN = "",
            binInfo = null,
            prevRequests = emptyList(),
            errorOnRequests = false,
            errorOnBINInfo = false
        )

    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {

            // interact with network, get information about BIN
            is UiEvent.OnLoadBINInfoClicked -> {

                Log.d("Hamster", "OnLoadBINInfoClicked, recentBIN = ${event.BIN}")

                val formatter = SimpleDateFormat("dd.MM.yyyy (EEEE), HH:mm:ss")
                formatter.timeZone = TimeZone.getTimeZone("Europe/Moscow")
                val calendar = Calendar.getInstance()
                val currentDateTime = formatter.format(calendar.time)

                val newRequest = RequestModel(
                    id = UUID.randomUUID().toString(),
                    BIN = "BIN: ${event.BIN}",
                    requestDateTime = currentDateTime
                )
                //recentBIN = event.BIN
                viewModelScope.launch {
                    requestInteractor.create(newRequest)
                    networkInteractor.getBINInfo(event.BIN).fold(
                        onError = {
                            processDataEvent(DataEvent.OnLoadBINInfoFail)
                        },
                        onSuccess = {
                            processDataEvent(DataEvent.OnLoadBINInfoSuccess(it))
                        }
                    )
                }
                val mutableRequests = previousState.prevRequests.toMutableList()
                mutableRequests.add(newRequest)
                return previousState.copy(BIN = event.BIN, prevRequests = mutableRequests)
            }
            is DataEvent.OnLoadBINInfoFail -> {
                return previousState.copy(errorOnBINInfo = true)
            }
            is DataEvent.OnLoadBINInfoSuccess -> {
                return previousState.copy(binInfo = event.binInfo, errorOnBINInfo = false)
            }
            is UiEvent.OnDeleteAllRequestsClicked -> {
                viewModelScope.launch {
                    requestInteractor.clearDB()
                }
                return previousState.copy(prevRequests = emptyList())
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
                return previousState.copy(errorOnRequests = true)
            }
            is DataEvent.OnLoadPreviousRequestsSuccess -> {
                return previousState.copy(prevRequests = event.requests, errorOnRequests = false)
            }

            else -> return null
        }
    }

}