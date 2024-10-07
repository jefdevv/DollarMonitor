package br.com.jef.dollarmonitor.state

import br.com.jef.dollarmonitor.service.Ticker

sealed class ScreenState {
    object Loading : ScreenState()
    data class Success(val data: Ticker) : ScreenState()
    data class Error(val exception: Throwable) : ScreenState()
}