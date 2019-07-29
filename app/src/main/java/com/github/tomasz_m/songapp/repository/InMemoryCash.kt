package com.github.tomasz_m.songapp.repository

import java.util.*


/**
 * simple cash implementation to avoid too many network calls
 */
class InMemoryCash<T> : Cash<T> {

    private var cash: T? = null
    private var timestamp: Long? = null
    private var cashId: String? = null

    override fun hasFreshCash(cashId: String): Boolean {
        if (cashId != this.cashId) {
            return false
        }
        if (timestamp == null) {
            return false
        }
        return Date().time - timestamp!! < 1000 * 20
    }

    override fun getLatestCash(cashId: String): T? {
        if (cashId != this.cashId) {
            return null
        }
        return cash
    }

    override fun setCash(cashId: String, data: T) {
        this.cashId = cashId
        cash = data
        timestamp = Date().time
    }


}