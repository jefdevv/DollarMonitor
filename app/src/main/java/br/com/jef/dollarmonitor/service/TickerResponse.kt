package br.com.jef.dollarmonitor.service

import android.hardware.biometrics.BiometricManager.Strings

class TickerResponse(
    val ticker: Ticker
)

class Ticker(
    val last: String
)
