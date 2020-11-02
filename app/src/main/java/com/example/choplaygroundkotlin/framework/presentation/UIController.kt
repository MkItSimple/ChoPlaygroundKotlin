package com.example.choplaygroundkotlin.framework.presentation

import com.example.choplaygroundkotlin.business.domain.state.DialogInputCaptureCallback
import com.example.choplaygroundkotlin.business.domain.state.Response
import com.example.choplaygroundkotlin.business.domain.state.StateMessageCallback

interface UIController {

    fun displayProgressBar(isDisplayed: Boolean)

    fun hideSoftKeyboard()

    fun displayInputCaptureDialog(title: String, callback: DialogInputCaptureCallback)

    fun onResponseReceived(
        response: Response,
        stateMessageCallback: StateMessageCallback
    )

}
