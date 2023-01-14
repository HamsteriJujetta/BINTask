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
import java.util.UUID

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
            binInfo = getDefaultBINInfoModel(),
            prevRequests = emptyList(),
            errorOnRequests = false,
            errorOnBINInfo = false
        )

    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {

            // interact with network, get information about BIN
            is UiEvent.OnLoadBINInfoClicked -> {
                val newRequest = RequestModel(
                    id = UUID.randomUUID().toString(),
                    BIN = event.BIN,
                    requestDateTime = "default date" // TODO
                )
                viewModelScope.launch {
                    requestInteractor.clearDB()
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

    private fun getDefaultBINInfoModel() = BINInfoModel(
        number = BINNumber(length = 0, luhn = false),
        scheme = "",
        type = "",
        brand = "",
        prepaid = false,
        country = BINCountry(
            numeric = "",
            alpha2 = "",
            name = "",
            emoji = "",
            currency = "",
            latitude = 0,
            longitude = 0
        ),
        bank = BINBank(
            name = "",
            url = "",
            phone = "",
            city = ""
        )
    )

}