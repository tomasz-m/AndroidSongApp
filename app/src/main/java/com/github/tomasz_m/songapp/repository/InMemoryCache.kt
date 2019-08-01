package com.github.tomasz_m.songapp.repository

import java.util.*


/**
 * simple cash implementation to avoid too many network calls
 */
class InMemoryCache<T> : Cache<T> {

    private var cash: T? = null
    private var timestamp: Long? = null
    private var cashId: String? = null

    override fun hasFreshCache(cashId: String): Boolean {
        if (cashId != this.cashId) {
            return false
        }
        if (timestamp == null) {
            return false
        }
        return Date().time - timestamp!! < 1000 * 2
    }

    override fun getLatestCache(cashId: String): T? {
        if (cashId != this.cashId) {
            return null
        }
        return cash
    }

    override fun setCache(cashId: String, data: T) {
        this.cashId = cashId
        cash = data
        timestamp = Date().time
    }


}