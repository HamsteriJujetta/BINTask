package com.example.bintask.base

import com.example.bintask.database.data.RequestModel
import com.example.bintask.network.data.models.BINInfoModel

data class ViewState(
    val BIN: String,
    val binInfo: BINInfoModel,
    val prevRequests: List<RequestModel>,
    val errorOnRequests: Boolean,
    val errorOnBINInfo: Boolean
)


sealed class UiEvent() : Event {
    data class OnLoadBINInfoClicked(val BIN: String) : UiEvent()
}


sealed class DataEvent() : Event {
    object OnLoadBINInfoFail : DataEvent()
    data class OnLoadBINInfoSuccess(val binInfo: BINInfoModel) : DataEvent()

    object OnLoadPreviousRequests : DataEvent()
    object OnLoadPreviousRequestsFail : DataEvent()
    data class OnLoadPreviousRequestsSuccess(val requests: List<RequestModel>) : DataEvent()
}