package com.example.bintask.base

import com.example.bintask.database.data.RequestModel

data class ViewState(
    val BIN: String,
    val prevRequests: List<RequestModel>,
    val isError: Boolean
)


sealed class UiEvent() : Event {
    data class OnLoadBINInfoClicked(val bin: String) : UiEvent()
}


sealed class DataEvent() : Event {
    //object OnLoadBINInfo : DataEvent()
    //object OnLoadBINInfoFail : DataEvent()
    //data class OnLoadBINInfoSuccess() : DataEvent()

    object OnLoadPreviousRequests : DataEvent()
    object OnLoadPreviousRequestsFail : DataEvent()
    data class OnLoadPreviousRequestsSuccess(val requests: List<RequestModel>) : DataEvent()
}